package com.sypark.data.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.sypark.data.interceptor.HttpRequestInterceptor
import com.sypark.data.service.OpenTicketClient
import com.sypark.data.service.OpenTicketService
import com.sypark.data.util.BaseUrlUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    val TAG = "NetworkModule"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(CookieManager()))
            .addInterceptor(HttpRequestInterceptor())
            .addInterceptor(loggingInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BaseUrlUtil.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message -> Log.e(TAG, message + "") }
        // BASIC
        // HEADERS
        // BODY
        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun instanceTicketService(retrofit: Retrofit): OpenTicketService {
        return retrofit.create(OpenTicketService::class.java)
    }

    @Provides
    @Singleton
    fun provideTicketClient(openTicketService: OpenTicketService): OpenTicketClient {
        return OpenTicketClient(openTicketService)
    }
}