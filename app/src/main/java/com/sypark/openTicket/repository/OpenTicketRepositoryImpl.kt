package com.sypark.openTicket.repository

import com.sypark.openTicket.model.OpenTicket
import com.sypark.openTicket.network.OpenTicketService
import com.sypark.openTicket.request.Request
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class OpenTicketRepositoryImpl(
    private val openTicketService: OpenTicketService
): OpenTicketRepository {

    override suspend fun requestMelonOpenTicket(request: Request): Flow<List<OpenTicket>> {
        return flow {
            val temp = openTicketService.requestMelonOpenTicket(request)
        }
    }


    override suspend fun getInterParkOpenData(): Flow<List<OpenTicket>> {
        TODO("Not yet implemented")
    }

    override suspend fun getYes24OpenData(): Flow<List<OpenTicket>> {
        TODO("Not yet implemented")
    }
}