package com.android.charly.logcatlogger;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jdandrade on 4/23/2017.
 * This type of interceptor will log everything in the corresponding builder request in your Android Monitor - Logcat
 */

public class LogcatLoggerInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private final long maxLength = 100000L;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Log.d(this.getClass().getCanonicalName(), String.format("Sending request %s on %s%n%s %s",
                request.url(), chain.connection(), request.headers(), request.body()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Log.d(this.getClass().getCanonicalName(), String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }
}
