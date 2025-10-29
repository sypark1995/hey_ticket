package com.sypark.openTicket.repository

import com.sypark.openTicket.model.OpenTicket
import com.sypark.openTicket.request.Request
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface OpenTicketRepository {
    suspend fun requestMelonOpenTicket(request: Request): Flow<List<OpenTicket>>

    suspend fun getInterParkOpenData(): Flow<List<OpenTicket>>

    suspend fun getYes24OpenData(): Flow<List<OpenTicket>>

}