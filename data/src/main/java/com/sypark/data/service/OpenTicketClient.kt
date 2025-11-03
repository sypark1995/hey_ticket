package com.sypark.data.service

import com.sypark.domain.model.OpenTicket
import com.sypark.domain.model.Request
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OpenTicketClient @Inject constructor(
    private val openTicketService: OpenTicketService
) {
    suspend fun requestInterParkTicket(
        request: Request
    ): Flow<List<OpenTicket>>? = openTicketService.requestInterParkTicket(
        request = request
    )
}