package com.example.homework_day_09.requester;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.homework_day_09.item.QuickGameItem;
import com.example.homework_day_09.listener.RequestOnResponseListener;
import com.example.homework_day_09.tools.CommonData;
import com.example.homework_day_09.tools.QuickGameList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpQuickGameRequester implements QuickGameRequester {
    private static final String TAG = "QuickGameRequester";
    private final OkHttpClient okHttpClient;
    private final Gson gson;

    public OkHttpQuickGameRequester(OkHttpClient okHttpClient, Gson gson) {
        this.okHttpClient = okHttpClient;
        this.gson = gson;
    }

    @Override
    public void request(String searchString, int current, RequestOnResponseListener listener) {
        String urlString = "https://hotfix-service-prod.g.mi.com/quick-game/game/search?search";

        if (!searchString.equals("")) {
            urlString += "=" + searchString;
        }
        urlString += "&current=" + current;

        Request request = new Request.Builder()
                .get()
                .url(urlString)
                .build();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
                Log.d(TAG, "request successful: " + response.code());
                assert response.body() != null;
                String string = response.body().string();
                Log.d(TAG, string);
                CommonData<QuickGameList<QuickGameItem>> commonData = gson.fromJson(
                        string, new TypeToken<CommonData<QuickGameList<QuickGameItem>>>() {}.getType()
                );
                listener.OnResponse(commonData);
            }
        });
    }
}
