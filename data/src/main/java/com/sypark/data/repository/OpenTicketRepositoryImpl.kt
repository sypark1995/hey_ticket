package com.sypark.data.repository

import com.sypark.data.service.OpenTicketClient
import com.sypark.domain.model.OpenTicket
import com.sypark.domain.model.Request
import com.sypark.domain.repository.OpenTicketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OpenTicketRepositoryImpl @Inject constructor(
    private val openTicketClient: OpenTicketClient
) : OpenTicketRepository {

    override suspend fun requestMelonOpenTicket(request: Request): Flow<List<OpenTicket>> {
        return flow {
            openTicketClient.requestInterParkTicket(request)?.collect {
                emit(it)
            }
        }
    }

    override suspend fun getInterParkOpenData(request: Request): Flow<List<OpenTicket>> {
        return flow {

        }
    }

    override suspend fun getYes24OpenData(request: Request): Flow<List<OpenTicket>> {
        return flow {

        }
    }

}