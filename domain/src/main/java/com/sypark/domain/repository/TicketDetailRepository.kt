package com.sypark.domain.repository

import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.TicketDetail
import kotlinx.coroutines.flow.Flow

interface TicketDetailRepository {
    suspend fun getTicketDetail(id: String): Flow<ApiResult<TicketDetail>>
}
