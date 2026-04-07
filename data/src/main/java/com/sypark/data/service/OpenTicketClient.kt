package com.sypark.data.service

import com.sypark.data.db.entity.BaseResponse
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

    suspend fun requestTicketDetail(
        id: String
    ): BaseResponse = openTicketService.requestPerformancesDetail(
        id
    )

    suspend fun requestPerformancesRanking(
        timePeriod: String,
        date: String,
        genre: String?,
        area: String?,
        page: Int?,
        pageSize: Int?
    ): BaseResponse = openTicketService.requestPerformancesRanking(
        timePeriod, date, genre, area, page, pageSize
    )

    suspend fun requestPerformanceNew(
        genre: String?,
        page: Int?,
        pageSize: Int?
    ): BaseResponse = openTicketService.requestPerformancesNew(
        genre,
        page,
        pageSize
    )

    suspend fun requestPerformanceCount(): BaseResponse =
        openTicketService.requestPerformancesCount()
}