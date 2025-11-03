package com.sypark.data.di

import com.google.gson.GsonBuilder
import com.sypark.data.service.OpenTicketService
import com.sypark.data.interceptor.HttpRequestInterceptor
import com.sypark.data.service.OpenTicketClient
import com.sypark.data.util.BaseUrlUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpRequestInterceptor())
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
    fun instanceTicketService(retrofit: Retrofit): OpenTicketService {
        return retrofit.create(OpenTicketService::class.java)
    }

    @Provides
    @Singleton
    fun provideTicketClient(openTicketService: OpenTicketService): OpenTicketClient {
        return OpenTicketClient(openTicketService)
    }
}