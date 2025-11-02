package com.sypark.data.datasource

import com.sypark.data.api.OpenTicketService
import com.sypark.domain.model.OpenTicket
import com.sypark.domain.model.Request
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OpenTicketDataSourceImpl(private val openTicketService: OpenTicketService): OpenTicketDataSource {

    override suspend fun getInterParkList(request: Request): Flow<List<OpenTicket>> {
        return flow {
            openTicketService.requestInterParkTicket(request).collect{
                emit(it)
            }
        }
    }

}