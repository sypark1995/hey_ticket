package com.sypark.openTicket.repository

import com.sypark.openTicket.model.OpenTicket
import com.sypark.openTicket.network.OpenTicketService
import com.sypark.openTicket.request.Request
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class OpenTicketRepositoryImpl(
    private val openTicketService: OpenTicketService
) : OpenTicketRepository {

    override suspend fun requestMelonOpenTicket(request: Request): Flow<List<OpenTicket>> {
        return flow {
            openTicketService.requestMelonOpenTicket(request).collect {
                emit(it)
            }
        }
    }

    override suspend fun getInterParkOpenData(request: Request): Flow<List<OpenTicket>> {
        return flow {
            openTicketService.requestInterParkTicket(request).collect {
                emit(it)
            }
        }
    }

    override suspend fun getYes24OpenData(request: Request): Flow<List<OpenTicket>> {
        return flow {
            openTicketService.requestYes24Ticket(request).collect {
                emit(it)
            }
        }
    }

}