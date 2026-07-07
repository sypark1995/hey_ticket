package com.sypark.data.di

import com.sypark.data.service.KopisApiService
import com.sypark.data.util.KopisConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KopisNetworkModule {

    @Provides
    @Singleton
    @KopisRetrofit
    fun provideKopisOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideKopisRetrofit(@KopisRetrofit okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(KopisConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideKopisApiService(retrofit: Retrofit): KopisApiService =
        retrofit.create(KopisApiService::class.java)
}
