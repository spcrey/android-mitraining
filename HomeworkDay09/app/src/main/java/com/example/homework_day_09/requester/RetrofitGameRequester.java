package com.example.homework_day_09.requester;

import androidx.compose.ui.platform.WeakCache;

import com.example.homework_day_09.HomeworkActivity;
import com.example.homework_day_09.item.QuickGameItem;
import com.example.homework_day_09.listener.RequestOnResponseListener;
import com.example.homework_day_09.tools.ApiService;
import com.example.homework_day_09.tools.CommonData;
import com.example.homework_day_09.tools.QuickGameList;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitGameRequester implements QuickGameRequester {
    private static final String TAG = "RetrofitGameRequester";
    private final ApiService apiService;
    public RetrofitGameRequester(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void request(String searchString, int current, RequestOnResponseListener listener) {
        retrofit2.Call<CommonData<QuickGameList<QuickGameItem>>> commonDataCall = apiService.search(searchString, current);
        commonDataCall.enqueue(new Callback<CommonData<QuickGameList<QuickGameItem>>>() {
            @Override
            public void onResponse(Call<CommonData<QuickGameList<QuickGameItem>>> call, Response<CommonData<QuickGameList<QuickGameItem>>> response) {
                assert response.body() != null;
                listener.OnResponse(response.body());
            }

            @Override
            public void onFailure(Call<CommonData<QuickGameList<QuickGameItem>>> call, Throwable t) {
            }
        });
    }
}
