package com.sypark.data.service

import com.sypark.data.db.entity.*
import com.sypark.data.db.entity.request.LoginVerification
import com.sypark.data.db.entity.request.RegisterValidationSend
import com.sypark.data.db.entity.request.RegisterValidationVerify
import com.sypark.data.db.entity.request.Signup
import retrofit2.http.*

interface OpenTicketService {

    @GET("/interpark")
    suspend fun requestInterParkTicket(
        @Query("genre")
        genre: String,

        @Query("order")
        order: String,

        @Query("pageIndex")
        pageIndex: String,

        @Query("size")
        size: String?,
    ): List<OpenTicket>?

    @GET("/api/v1/performances")
    suspend fun requestPerformances(
        @Query("page")
        page: Int,

        @Query("size")
        size: Int
    ): List<Ticket>

    @GET("/api/performances/{id}")
    suspend fun requestPerformancesDetail(
        @Path("id")
        id: String
    ): BaseResponse

    @GET("/api/performances/rank")
    suspend fun requestPerformancesRanking(
        @Query("timePeriod")
        timePeriod: String?,
        @Query("date")
        date: String?,
        @Query("genre")
        genre: String?,
        @Query("area")
        area: String?,
        @Query("page")
        page: Int?,
        @Query("pageSize")
        pageSize: Int?,
    ): BaseResponse

    @GET("/api/performances/new")
    suspend fun requestPerformancesNew(
        @Query("genre")
        genre: String?,
        @Query("page")
        page: Int?,
        @Query("pageSize")
        pageSize: Int?
    ): BaseResponse

    @GET("/api/performances/genres/count")
    suspend fun requestPerformancesCount(
    ): BaseResponse

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