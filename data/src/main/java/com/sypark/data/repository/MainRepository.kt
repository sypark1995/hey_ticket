package com.sypark.data.repository

import com.sypark.data.db.entity.BaseResponse
import com.sypark.data.db.entity.OpenTicket
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun getInterParkOpenTicket(
        genre: String,
        order: String,
        pageIndex: String,
        size: String?,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<OpenTicket>>?

    suspend fun getRankingTicket(
        timePeriod: String,
        date: String,
        genre: String?,
        area: String?,
        page: Int? = 0,
        pageSize: Int? = 10
    ): Flow<BaseResponse>

    suspend fun getNewTicket(
        genre: String?,
        page: Int? = 0,
        pageSize: Int? = 10
    ): Flow<BaseResponse>
}