package com.sypark.data.service

import com.sypark.data.db.entity.*
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenTicketService {

    @GET("/melon")
    suspend fun requestMelonOpenTicket(
        @Query("genre")
        genre: String,

        @Query("order")
        order: String,

        @Query("pageIndex")
        pageIndex: String,

        @Query("size")
        size: String?,
    ): List<OpenTicket>

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

    @GET("/yes24")
    suspend fun requestYes24Ticket(
        @Query("genre")
        genre: String,

        @Query("order")
        order: String,

        @Query("pageIndex")
        pageIndex: String,

        @Query("size")
        size: String?,
    ): Flow<List<OpenTicket>>

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

    @GET("/api/v1/places/{mt10id}")
    suspend fun requestPerformancesPlaceDetail(
        @Path("mt10id")
        mt10id: String
    ): PlaceDetail

    @GET("/api/performances/rank")
    suspend fun requestPerformancesRanking(
        @Query("timePeriod")
        timePeriod: String,
        @Query("date")
        date: String,
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
}