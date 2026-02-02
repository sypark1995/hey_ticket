package com.sypark.data.service

import com.sypark.data.db.entity.OpenTicket
import com.sypark.data.db.entity.Ticket
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
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
}