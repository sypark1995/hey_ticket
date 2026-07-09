package com.sypark.data.db.entity

import com.sypark.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

fun <T> safeFlow(service: suspend () -> T): Flow<ApiResult<T>> = flow<ApiResult<T>> {
    emit(ApiResult.Success(service.invoke()))
}.catch { e ->
    when (e) {
        is HttpException -> emit(ApiResult.Error(code = e.code(), exception = e))
        else -> emit(ApiResult.Error(exception = e))
    }
}
