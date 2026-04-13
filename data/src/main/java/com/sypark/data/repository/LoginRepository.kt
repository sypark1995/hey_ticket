package com.sypark.data.repository

import com.sypark.data.db.entity.ApiResult
import com.sypark.data.db.entity.BaseResponse
import com.sypark.data.db.entity.LoginVerification
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun getLoginValidation(loginVerification: LoginVerification): Flow<ApiResult<BaseResponse>>
}