package com.sypark.data.datasource

import com.sypark.domain.model.OpenTicket
import com.sypark.domain.model.Request
import kotlinx.coroutines.flow.Flow

interface OpenTicketDataSource {
    suspend fun getInterParkList(request: Request) : Flow<List<OpenTicket>>
}