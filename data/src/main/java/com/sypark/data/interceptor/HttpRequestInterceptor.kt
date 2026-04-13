package com.sypark.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response

internal class HttpRequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder().header("accept","*/*").url(originalRequest.url).build()
        return chain.proceed(request)
    }
}