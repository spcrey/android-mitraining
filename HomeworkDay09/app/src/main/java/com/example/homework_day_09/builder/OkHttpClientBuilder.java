package com.example.homework_day_09.builder;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpClientBuilder {

    private static final String TAG = "OkHttpClientBuilder";
    public static OkHttpClient build() {
            return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Log.d(TAG, "first intercept");
                        Request request = chain.request();
                        Request.Builder requestBuilder = request.newBuilder();
                        requestBuilder.addHeader("userId", "spcrey");
                        requestBuilder.addHeader("token", "token82513");
                        requestBuilder.addHeader("versionCode", "120");
                        requestBuilder.addHeader("versionName", "1.2.0");

                        if (TextUtils.equals(request.method(), "GET")) {
                            HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
                            requestBuilder.url(httpUrlBuilder.build());
                        }
                        return chain.proceed(requestBuilder.build());
                    }
                })
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Log.d(TAG, "second intercept");
                        Request request = chain.request();
                        Log.d(TAG, "[intercept 2] request: " + request.toString());
                        String method = request.method();
                        Response response = chain.proceed(request);
                        int code = response.code();
                        Log.d(TAG, "[intercept 2] code: " + code);
                        ResponseBody responseBody = response.body();
                        if (responseBody != null) {
                            String string = responseBody.string();
                            Log.d(TAG, "[intercept 2] response body: " + string);
                            return response.newBuilder().body(ResponseBody.create(
                                    responseBody.contentType(), string
                            )).build();
                        }
                        return response;
                    }
                })
                .build();
    }
}
