package com.sypark.domain.repository

import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.Content
import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    suspend fun getPerformanceList(genreCode: String?, page: Int, rows: Int): Flow<ApiResult<List<Content>>>
    suspend fun getNew(genreCode: String?, page: Int, rows: Int): Flow<ApiResult<List<Content>>>
    suspend fun getDetail(performanceId: String): Flow<ApiResult<Content>>
    suspend fun search(query: String, page: Int, rows: Int): Flow<ApiResult<List<Content>>>
    suspend fun getClosingSoon(rows: Int): Flow<ApiResult<List<Content>>>
    suspend fun getMatchingNew(genreCode: String?, areaCode: String?, rows: Int): Flow<ApiResult<List<Content>>>
}
