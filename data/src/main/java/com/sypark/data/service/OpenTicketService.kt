package com.sypark.data.service

import com.sypark.domain.model.OpenTicket
import com.sypark.domain.model.Request
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET

interface OpenTicketService {

    @GET("/melon")
    suspend fun requestMelonOpenTicket(
        @Body request: Request
    ): Flow<List<OpenTicket>>

    @GET("/interpark")
    suspend fun requestInterParkTicket(
        @Body request: Request
    ): Flow<List<OpenTicket>>?

    @GET("/yes24")
    suspend fun requestYes24Ticket(
        @Body request: Request
    ): Flow<List<OpenTicket>>
}