package com.android.charly.logcatlogger;

import android.util.Log;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
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
    private final long maxByteCount = 100000L;

    @Override
    public Response intercept(Chain chain) throws IOException {

        /*
        this is the okhttp interceptor example.
         */
        Request request = chain.request();

        long t1 = System.nanoTime();
        Log.d(this.getClass().getCanonicalName(), String.format("Sending request %s on %s%n%s %s",
                request.url(), chain.connection(), request.headers(), request.body()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Log.d(this.getClass().getCanonicalName(), String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        /*
        okhttp example ends here
         */

        BufferedSource bufferedSource = getSource(response);
        bufferedSource.request(Long.MAX_VALUE);
        Buffer buffer = bufferedSource.buffer();

        Log.d(this.getClass().getCanonicalName(), readFromBuffer(buffer.clone(), UTF8));
        return response;
    }

    private String readFromBuffer(Buffer bufferClone, Charset charset) {
        long bufferSize = bufferClone.size();
        long maxBytes = Math.min(bufferSize, maxByteCount);
        String body = "";
        try{
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
        }else
            return bufferedSource;
    }

    private boolean bodyGzipped(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return "gzip".equalsIgnoreCase(contentEncoding);
    }
}
