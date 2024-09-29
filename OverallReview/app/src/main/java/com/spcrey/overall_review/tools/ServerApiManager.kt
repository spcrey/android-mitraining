package com.spcrey.overall_review.tools

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object ServerApiManager {
    private const val TAG = "ServerApiManager"
    interface ApiService {
        @GET("quick-game/game/{id}")
        fun queryGame(@Path("id") id: String): Deferred<CommonData<GameItem>>
    }
    val interceptor = Interceptor { chain ->
        return@Interceptor chain.proceed(chain.request())
    }
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(interceptor)
        .build()
    private val gson = Gson()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://hotfix-service-prod.g.mi.com")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build();
    val apiService = retrofit.create(ApiService::class.java)
    data class CommonData<T>(val code: Int, val msg: String, val data: T)
    data class GameItem(val gameName: String, val packageName: String)

    @OptIn(DelicateCoroutinesApi::class)
    @JvmStatic
    fun main(args: Array<String>) {
        println("0000")
        GlobalScope.launch {
            try {
                delay(2000)
                val commonData: CommonData<GameItem> = apiService.queryGame("109").await()
                println("request successful")
                println(commonData.toString())
            } catch (e: Exception) {
                println("request failed: ${e.message}")
            }
        }
        Thread.sleep(4000)
        println("1111")
    }
}