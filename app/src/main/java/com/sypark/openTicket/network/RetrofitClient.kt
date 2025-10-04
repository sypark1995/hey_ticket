package com.sypark.openTicket.network

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    class AppInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val newRequest = request().newBuilder()
//                .addHeader("Content-Type", "application/json; charset=euc-kr")
                .build()

            proceed(newRequest)
        }
    }

    private fun provideOkHttpClient(interceptor: AppInterceptor): OkHttpClient =
        provideOkHttpClient(interceptor, false)

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message ->
            Log.e(
                "TAG",
                message + ""
            )
        }
        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private fun provideOkHttpClient(interceptor: AppInterceptor, image: Boolean): OkHttpClient =
        OkHttpClient.Builder().run {
            addInterceptor(interceptor)
            addInterceptor(loggingInterceptor())

//            if (image) {
//                // 이미지 업데이트 시 타임아웃 시간을 10 -> 60 으로 변경
//                readTimeout(60, TimeUnit.SECONDS)
//                writeTimeout(60, TimeUnit.SECONDS)
//                connectTimeout(60, TimeUnit.SECONDS)
//            } else {

//            }
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            connectTimeout(30, TimeUnit.SECONDS)

            build()
        }

    fun instanceInterPark(): RetrofitService {
        //   Log.e("Server", "Server URL  $serverURL")

        return Retrofit.Builder()
            .baseUrl(BaseUrlUtil.interParkUrl2)
            .client(provideOkHttpClient(AppInterceptor()))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }
}