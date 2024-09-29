package com.xiaomi.weibo_hanpeng.tools

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.xiaomi.weibo_hanpeng.LoginActivity
import com.xiaomi.weibo_hanpeng.PageActivity
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.lang.ref.WeakReference
import java.util.Random
import java.util.concurrent.TimeUnit

object ServerApiManager {
    private const val TAG = "ServerApiManager"

    private var pageActivity:PageActivity? = null

    fun bindContext(pageActivity: PageActivity) {
        this.pageActivity = pageActivity
    }

    interface Event403Listener {
        fun on403()
    }

    var event403Listener: Event403Listener? = null

    fun setEvent403Listener222(listener: Event403Listener) {
        event403Listener = listener
    }

    interface ApiService {
        @Headers("content-type: application/json")
        @POST("weibo/api/auth/sendCode")
        fun sendCode(@Body phoneRequest: PhoneRequest): Deferred<CommonData<String>>

        @Headers("content-type: application/json")
        @POST("weibo/api/auth/login")
        fun login(@Body phoneCodeRequest: PhoneCodeRequest): Deferred<CommonData<String>>

        @Headers("content-type: application/json")
        @GET("weibo/api/user/info")
        fun info(@Header("Authorization") token: String): Deferred<CommonData<UserInfo>>


        @Headers("content-type: application/json")
        @GET("weibo/homePage")
        fun homePage(@Header("Authorization") token: String?=null,
            @Query("current") current: Int = 1): Deferred<CommonData<HomePage>>

        @Headers("content-type: application/json")
        @POST("weibo/like/up")
        fun likeUp(@Header("Authorization") token: String, @Body idRequest: IdRequest): Deferred<CommonData<Boolean>>

        @Headers("content-type: application/json")
        @POST("weibo/like/down")
        fun likeDown(@Header("Authorization") token: String, @Body idRequest: IdRequest): Deferred<CommonData<Boolean>>

    }

//    var num = 0

    private val interceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val originalResponse = chain.proceed(originalRequest)
        val code = originalResponse.code()
//        num += 1
//        if(num ==5) {
//            event403Listener?.on403()
//        }
        if (code==403)
            event403Listener?.on403()

        return@Interceptor originalResponse
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .callTimeout(5, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://hotfix-service-prod.g.mi.com")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build();

    val apiService: ApiService = retrofit.create(ApiService::class.java)

    data class CommonData<T>(val code: Int, val msg: String, val data: T)
    data class UserInfo(val id: Long, val username: String, val phone: String, val avatar: String, val loginStatus: Boolean)

    data class PhoneRequest(val phone: String)
    data class PhoneCodeRequest(val phone: String, val smsCode: String)

    data class IdRequest(val id: Long)
    data class HomePage(
        val records: List<WeiboInfo>, val total: Int, val size: Int,
        val current: Int, val pages: Int)
    data class WeiboInfo(
        val id: Long, val userId: Long, val username: String,
        val phone: String, val avatar: String, val title: String,
        val videoUrl: String?, val poster: String?, val images: List<String>?,
        var likeCount: Int, var likeFlag: Boolean, val createTime: String
    )

    @JvmStatic
    fun main(args: Array<String>) {
        print("Hello World!")
    }
}
