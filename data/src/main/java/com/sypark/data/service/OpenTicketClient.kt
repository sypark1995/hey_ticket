package com.sypark.data.service

import com.sypark.data.db.entity.OpenTicket
import javax.inject.Inject

class OpenTicketClient @Inject constructor(
    private val openTicketService: OpenTicketService
) {
    suspend fun requestInterParkTicket(
        genre: String,
        order: String,
        pageIndex: String,
        size: String?,
    ): List<OpenTicket>? = openTicketService.requestInterParkTicket(
        genre,
        order,
        pageIndex,
        size
    )

    suspend fun requestMelonTicket(
        genre: String,
        order: String,
        pageIndex: String,
        size: String?,
    ): List<OpenTicket>? = openTicketService.requestMelonOpenTicket(
        genre,
        order,
        pageIndex,
        size
    )
}