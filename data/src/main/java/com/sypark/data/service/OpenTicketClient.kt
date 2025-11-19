package com.sypark.data.service

import com.sypark.data.db.entity.OpenTicketEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OpenTicketClient @Inject constructor(
    private val openTicketService: OpenTicketService
) {
    suspend fun requestInterParkTicket(
        genre: String,
        order: String,
        pageIndex: String,
        size: String?,
    ): List<OpenTicketEntity>? = openTicketService.requestInterParkTicket(
        genre,
        order,
        pageIndex,
        size
    )
}