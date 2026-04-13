package com.sypark.data.db.entity

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

sealed class ApiResult<out T> {
    data class Success<out T>(val value: T): ApiResult<T>()
    object Loading: ApiResult<Nothing>()
    data class Error(val code: Int? = null, val exception: Throwable? = null): ApiResult<Nothing>()
}

fun <T> safeFlow(apiFunc: suspend () -> T): Flow<ApiResult<T>> = flow {
    try {
        emit(ApiResult.Success(apiFunc.invoke()))
    } catch (e: NullPointerException) {
        emit(ApiResult.Loading)
    } catch (e: HttpException) {
        emit(ApiResult.Error(code = e.code(), exception = e))
    } catch (e: Exception) {
        emit(ApiResult.Error(exception = e))
    }
}