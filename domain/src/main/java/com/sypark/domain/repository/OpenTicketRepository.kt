package com.sypark.domain.repository

import com.sypark.domain.model.OpenTicket
import com.sypark.domain.model.Request
import kotlinx.coroutines.flow.Flow

interface OpenTicketRepository {

    suspend fun requestMelonOpenTicket(request: Request): Flow<List<OpenTicket>>

    suspend fun getInterParkOpenData(request: Request): Flow<List<OpenTicket>>

    suspend fun getYes24OpenData(request: Request): Flow<List<OpenTicket>>

}