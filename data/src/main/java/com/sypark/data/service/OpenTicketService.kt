package com.sypark.data.service

import com.sypark.data.db.entity.BaseResponse
import com.sypark.data.db.entity.request.LoginVerification
import com.sypark.data.db.entity.request.RegisterValidationSend
import com.sypark.data.db.entity.request.RegisterValidationVerify
import com.sypark.data.db.entity.request.Signup
import retrofit2.http.Body
import retrofit2.http.POST

interface OpenTicketService {

    @POST("/api/members/validation")
    suspend fun requestLoginValidation(
        @Body
        loginVerification: LoginVerification
    ): BaseResponse

    @POST("/api/members/verification/send")
    suspend fun requestRegisterVerificationSend(
        @Body
        registerValidationSend: RegisterValidationSend
    ): BaseResponse

    @POST("/api/members/verification/verify")
    suspend fun requestVerify(
        @Body
        registerValidationVerify: RegisterValidationVerify
    ): BaseResponse

    @POST("/api/members/signup")
    suspend fun requestSignUp(
        @Body
        signup: Signup
    ): BaseResponse

}
