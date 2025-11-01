package com.sypark.openTicket.network

import com.sypark.openTicket.model.OpenTicket
import com.sypark.openTicket.request.Request
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
    ): Flow<List<OpenTicket>>

    @GET("/yes24")
    suspend fun requestYes24Ticket(
        @Body request: Request
    ): Flow<List<OpenTicket>>
}
