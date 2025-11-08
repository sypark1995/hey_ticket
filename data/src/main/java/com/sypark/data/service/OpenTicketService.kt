package com.sypark.data.service

import com.sypark.domain.model.OpenTicket
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
    ): Flow<List<OpenTicket>>

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
    ): Flow<List<OpenTicket>>?

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
}