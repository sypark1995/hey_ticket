package com.sypark.data.repository

import com.sypark.data.db.entity.ApiResult
import com.sypark.data.db.entity.BaseResponse
import com.sypark.data.db.entity.request.LoginVerification
import com.sypark.data.db.entity.request.RegisterValidationSend
import com.sypark.data.db.entity.request.RegisterValidationVerify
import com.sypark.data.db.entity.safeFlow
import com.sypark.data.service.OpenTicketClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val openTicketClient: OpenTicketClient,
) : LoginRepository {

    override suspend fun getLoginValidation(loginVerification: LoginVerification): Flow<ApiResult<BaseResponse>> =
        safeFlow {
            openTicketClient.requestLoginValidation(loginVerification)
        }

    override suspend fun getRegisterValidationSend(registerValidationSend: RegisterValidationSend): Flow<ApiResult<BaseResponse>> =
        safeFlow {
            openTicketClient.requestRegisterValidationSend(registerValidationSend)
        }

    override suspend fun getRegisterVerify(registerValidationVerify: RegisterValidationVerify): Flow<ApiResult<BaseResponse>> =
        safeFlow {
            openTicketClient.requestVerify(registerValidationVerify)
        }

}