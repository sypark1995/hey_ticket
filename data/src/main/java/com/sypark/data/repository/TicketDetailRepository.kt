package com.sypark.data.repository

import com.sypark.data.db.entity.BaseResponse
import kotlinx.coroutines.flow.Flow

interface TicketDetailRepository {

    suspend fun getTicketDetail(
        id: String,
    ): Flow<BaseResponse>
}