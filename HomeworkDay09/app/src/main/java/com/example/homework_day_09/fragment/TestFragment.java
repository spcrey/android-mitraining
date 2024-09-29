package com.example.homework_day_09.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.homework_day_09.R;
import com.example.homework_day_09.tools.CommonData;
import com.example.homework_day_09.tools.GameItem;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class TestFragment extends Fragment {

    private String requestBodyToString(RequestBody body) {
        return "body";
    }

    private static final Gson gson = new Gson();

    private final String phone = "18243280995";

    private Button button;

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                button.setText((CharSequence) msg.obj);
            }
        }
    };

    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
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
                        httpUrlBuilder.addQueryParameter("userId", "spcrey");
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
                    if (TextUtils.equals(method, "POST")) {
                        RequestBody requestBody = request.body();
                        assert requestBody != null;
                        Log.d(TAG, "[intercept 2] request body: " + requestBodyToString(requestBody));
                    }
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

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://hotfix-service-prod.g.mi.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    interface ApiService {
        @GET("quick-game/game/{id}")
        retrofit2.Call<CommonData<GameItem>> queryGame(@Path("id") String id);

        @POST("quick-game/api/auth/sendCode")
        retrofit2.Call<CommonData<String>> sendCode(@Header("content-type") String content_type, @Body RequestBody requestBody);

        @POST("quick-game/api/auth/sendCodeByFormData")
        @FormUrlEncoded
        retrofit2.Call<CommonData<String>> sendCodeFormData(@Field("phone") String phone);
    }
    private final ApiService apiService = retrofit.create(ApiService.class);

    private static final String TAG = "TestFragment";

    public TestFragment() {
        super(R.layout.fragment_test);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addButton(view);
    }

    private void addButton(View view) {
        button = new Button(getContext());
        LinearLayout layout = view.findViewById(R.id.layout_fragment_test);
        button.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelSize(R.dimen.button_height))
        );
        button.setText("dynamic button");
        button.setBackgroundResource(R.drawable.rounded_button_bg);
        ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams)
                        button.getLayoutParams();
        int margin = getResources().getDimensionPixelSize(R.dimen.margin);
        params.setMargins(margin, margin, margin, margin);
        button.setLayoutParams(params);
        layout.addView(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }

    private void retrofitGet() {
        retrofit2.Call<CommonData<GameItem>> commonDataCall = apiService.queryGame("109");
        commonDataCall.enqueue(new retrofit2.Callback<CommonData<GameItem>>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<CommonData<GameItem>> call, @NonNull retrofit2.Response<CommonData<GameItem>> response) {
                Log.d(TAG, "retrofit successful");
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<CommonData<GameItem>> call, @NonNull Throwable t) {
                Log.d(TAG, "retrofit failed");
            }
        });
    }

    private void retrofitPost() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("phone", phone);
        String json = gson.toJson(hashMap);
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json;char"),
                json);
        String content_type = "application/json";
        retrofit2.Call<CommonData<String>> commonDataCall = apiService.sendCode(content_type, requestBody);
        commonDataCall.enqueue(new retrofit2.Callback<CommonData<String>>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<CommonData<String>> call, @NonNull retrofit2.Response<CommonData<String>> response) {
                Log.d(TAG, "request successful");
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<CommonData<String>> call, @NonNull Throwable t) {
                Log.d(TAG, "request failed");
            }
        });
    }

    private void retrofitPostForm() {
        retrofit2.Call<CommonData<String>> commonDataCall = apiService.sendCodeFormData(phone);
        commonDataCall.enqueue(new retrofit2.Callback<CommonData<String>>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<CommonData<String>> call, @NonNull retrofit2.Response<CommonData<String>> response) {
                Log.d(TAG, "request successful");
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<CommonData<String>> call, @NonNull Throwable t) {
                Log.d(TAG, "request failed");
            }
        });
    }

    private void requestGet() {
        Request request = new Request.Builder()
                .get()
                .url("https://hotfix-service-prod.g.mi.com/quick-game/game/109")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "request failed");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d(TAG, "request successful: " + response.code());
            }
        });
    }

    private void requestPost() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("phone", phone);
        String json = gson.toJson(hashMap);
        Log.d(TAG, json);

        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json;char"),
                        json);
        Request request = new Request.Builder().post(requestBody)
                .addHeader("content-type", "application/json")
                .url("https://hotfix-service-prod.g.mi.com/quick-game/api/auth/sendCode")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "request failed");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d(TAG, "request successful, code: " + response.code());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        button.setText("new button 1");
                    }
                });
                Message message = Message.obtain();
                message.what = 1;
                message.obj = "new button 2";
                handler.sendMessageDelayed(message, 2000);
            }
        });

    }

    private void requestPostByFormData() {
        FormBody requestBody = new FormBody.Builder()
                .add("phone", phone)
                .build();

        String postFormString = "https://hotfix-service-prod.g.mi.com/quick-game/api/auth/sendCodeByFormData";
        Request request = new Request.Builder().post(requestBody)
                .addHeader("content-type", "application/json")
                .url(postFormString)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "request failed");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d(TAG, "request successful, code: " + response.code());
                assert response.body() != null;
                Log.d(TAG, "response body: " + response.body().string());
            }
        });
    }

    private void call() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:+8618243280995"));
        startActivity(intent);
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivity(intent);
    }

    private void scanSd() {
            File file = Environment.getExternalStorageDirectory();
            File[] files = file.listFiles();
            Log.d(TAG, "sd files: " + Arrays.toString(files));
            File text = new File(file, "new_file.txt");
            if (text.exists()) {
                boolean delete_file = text.delete();
                Log.d(TAG, "delete file: " + delete_file);
            }
            try {
                FileWriter fileWriter = new FileWriter(text);
                fileWriter.write("hello sd card");
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    private void scanSdWithPermission() {

    }

    private void callWithPermission() {
        int flag = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE);
        if (flag == PackageManager.PERMISSION_GRANTED) {
            call();
        }
        else {
            requestPermissions(
                    new String[] {
                            Manifest.permission.CALL_PHONE
                    }, 200
            );
        }
    }

    private void startCameraWithPermission() {
        int flag = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA);
        if (flag == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        }
        else {
            requestPermissions(
                    new String[] {
                            Manifest.permission.CAMERA
                    }, 200
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "permission request successful", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getContext(), "permission request failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}