package com.example.homework_day_09.tools;

import com.example.homework_day_09.item.QuickGameItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("quick-game/game/search")
    Call<CommonData<QuickGameList<QuickGameItem>>> search(@Query("search") String search, @Query("current") int current);
}
