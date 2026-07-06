package com.sypark.data.db.entity

import com.sypark.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

fun <T> safeFlow(service: suspend () -> T): Flow<ApiResult<T>> = flow {
    try {
        emit(ApiResult.Success(service.invoke()))
    } catch (e: NullPointerException) {
        emit(ApiResult.Loading)
    } catch (e: HttpException) {
        emit(ApiResult.Error(code = e.code(), exception = e))
    } catch (e: Exception) {
        emit(ApiResult.Error(exception = e))
    }
}
