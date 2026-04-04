package com.sypark.data.repository

import com.sypark.data.db.entity.BaseResponse
import com.sypark.data.service.OpenTicketClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TicketDetailRepositoryImpl @Inject constructor(
    private val openTicketClient: OpenTicketClient
) : TicketDetailRepository {

    override suspend fun getTicketDetail(id: String): Flow<BaseResponse> = flow {
        val data = openTicketClient.requestTicketDetail(id)
        emit(data)
    }
}