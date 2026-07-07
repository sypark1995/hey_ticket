package com.sypark.data.service

import com.sypark.data.db.entity.BaseResponse
import com.sypark.data.db.entity.request.LoginVerification
import com.sypark.data.db.entity.request.RegisterValidationSend
import com.sypark.data.db.entity.request.RegisterValidationVerify
import com.sypark.data.db.entity.request.Signup
import javax.inject.Inject

class OpenTicketClient @Inject constructor(
    private val openTicketService: OpenTicketService
) {
    suspend fun requestLoginValidation(
        loginVerification: LoginVerification
    ): BaseResponse = openTicketService.requestLoginValidation(
        loginVerification
    )

    suspend fun requestRegisterValidationSend(
        registerValidationSend: RegisterValidationSend
    ): BaseResponse = openTicketService.requestRegisterVerificationSend(
        registerValidationSend
    )

    suspend fun requestVerify(
        registerValidationVerify: RegisterValidationVerify
    ): BaseResponse = openTicketService.requestVerify(
        registerValidationVerify
    )

    suspend fun requestSignUp(
        signup: Signup
    ): BaseResponse = openTicketService.requestSignUp(
        signup
    )
}
