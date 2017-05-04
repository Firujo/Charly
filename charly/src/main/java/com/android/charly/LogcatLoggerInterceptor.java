package com.android.charly;

import android.content.Context;
import android.util.Log;

import com.android.charly.data.HttpRequest;
import com.android.charly.utils.NotificationHelper;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;

/**
 * Created by jdandrade on 4/23/2017.
 * This type of interceptor will log everything in the corresponding builder request in your Android Monitor - Logcat
 */

public class LogcatLoggerInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private final long maxByteCount = 200000L;

    private final NotificationHelper notificationHelper;
    private final boolean showNotification;
    private final Context context;

    public LogcatLoggerInterceptor(Context context) {
        this.context = context.getApplicationContext();
        notificationHelper = new NotificationHelper(this.context);
        showNotification = true;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setMethod(request.method());
        httpRequest.setUrl(request.url().toString());
        httpRequest.setRequestHeaders(request.headers());
        if (requestBody != null) {

            BufferedSource source = getSource(new Buffer(), bodyGzipped(request.headers()));
            Buffer buffer = source.buffer();
            requestBody.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            if (isPlaintext(buffer)) {
                httpRequest.setRequestBody(readFromBuffer(buffer, charset));
            }
        }

        long startTime = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            httpRequest.setError(e.toString());
            throw e;
        }
        long requestDuration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        ResponseBody responseBody = response.body();

        httpRequest.setRequestHeaders(response.request().headers()); // includes headers added later in the chain
        httpRequest.setResponseDate(new Date());
        httpRequest.setRequestDuration(requestDuration);
        httpRequest.setResponseCode(response.code());
        httpRequest.setResponseMessage(response.message());

        httpRequest.setResponseHeaders(response.headers());

        if (HttpHeaders.hasBody(response)) {
            BufferedSource source = getSource(response);
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {

                    return response;
                }
            }
            if (isPlaintext(buffer)) {
                httpRequest.setResponseBody(readFromBuffer(buffer.clone(), charset));
            }
        }

        Log.d(this.getClass().getCanonicalName(), httpRequest.toString());

        showNotification(httpRequest);

        return response;
    }

    private void showNotification(HttpRequest httpRequest) {
        if (showNotification) {
            notificationHelper.show(httpRequest);
        }
    }

    private String readFromBuffer(Buffer bufferClone, Charset charset) {
        long bufferSize = bufferClone.size();
        long maxBytes = Math.min(bufferSize, maxByteCount);
        String body = "";
        try {
            body = bufferClone.readString(maxBytes, charset);
        } catch (EOFException e) {
            body += "unexpected end of file";
        }
        if (bufferSize > maxByteCount) {
            body += "truncated";
        }
        return body;
    }

    private BufferedSource getSource(Response response) throws IOException {
        if (bodyGzipped(response.headers())) {
            BufferedSource bufferedSource = response.peekBody(maxByteCount).source();
            if (bufferedSource.buffer().size() < maxByteCount) {
                return getSource(bufferedSource, true);
            } else {
                Log.d(this.getClass().getCanonicalName(), "gzip encoded response is too long");
            }
        }
        return response.body().source();
    }

    private BufferedSource getSource(BufferedSource bufferedSource, boolean isGzipped) {
        if (isGzipped) {
            GzipSource source = new GzipSource(bufferedSource);
            return Okio.buffer(source);
        } else
            return bufferedSource;
    }

    private boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyGzipped(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return "gzip".equalsIgnoreCase(contentEncoding);
    }
}
