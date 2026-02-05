package com.sypark.data.repository

import com.sypark.data.db.entity.TicketDetail

interface TicketDetailRepository {

    suspend fun getTicketDetail(
        mt20id: String,
    ): TicketDetail?
}