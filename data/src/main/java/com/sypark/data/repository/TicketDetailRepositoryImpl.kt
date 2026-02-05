package com.sypark.data.repository

import com.sypark.data.db.entity.TicketDetail
import com.sypark.data.service.OpenTicketClient
import javax.inject.Inject

class TicketDetailRepositoryImpl @Inject constructor(
    private val openTicketClient: OpenTicketClient
) : TicketDetailRepository {

    override suspend fun getTicketDetail(mt20id: String): TicketDetail {
        return openTicketClient.requestTicketDetail(mt20id)
    }
}