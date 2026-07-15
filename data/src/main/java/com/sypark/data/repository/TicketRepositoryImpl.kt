package com.sypark.data.repository

import com.sypark.data.AppDispatchers
import com.sypark.data.BuildConfig
import com.sypark.data.Dispatcher
import com.sypark.data.db.entity.safeFlow
import com.sypark.data.mapper.KopisGenreMapper
import com.sypark.data.mapper.KopisXmlParser
import com.sypark.data.service.KopisApiService
import com.sypark.data.util.DateRange
import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.Content
import com.sypark.domain.repository.TicketRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val kopisApiService: KopisApiService,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : TicketRepository {

    override suspend fun getPerformanceList(genreCode: String?, page: Int, rows: Int): Flow<ApiResult<List<Content>>> =
        safeFlow {
            withContext(ioDispatcher) {
                val (start, end) = DateRange.todayToDaysAhead(90)
                val xml = kopisApiService.requestPerformanceList(
                    serviceKey = BuildConfig.KOPIS_SERVICE_KEY,
                    startDate = start,
                    endDate = end,
                    page = page,
                    rows = rows,
                    genreCode = genreCode,
                )
                KopisXmlParser.parsePerformanceList(xml)
            }
        }

    override suspend fun getNew(genreCode: String?, page: Int, rows: Int): Flow<ApiResult<List<Content>>> =
        safeFlow {
            withContext(ioDispatcher) {
                val (start, end) = DateRange.todayToDaysAhead(30)
                val xml = kopisApiService.requestPerformanceList(
                    serviceKey = BuildConfig.KOPIS_SERVICE_KEY,
                    startDate = start,
                    endDate = end,
                    page = page,
                    rows = rows,
                    genreCode = KopisGenreMapper.toShcate(genreCode),
                )
                KopisXmlParser.parsePerformanceList(xml)
            }
        }

    override suspend fun getDetail(performanceId: String): Flow<ApiResult<Content>> =
        safeFlow {
            withContext(ioDispatcher) {
                val xml = kopisApiService.requestPerformanceDetail(
                    performanceId = performanceId,
                    serviceKey = BuildConfig.KOPIS_SERVICE_KEY,
                )
                KopisXmlParser.parsePerformanceDetail(xml)
            }
        }

    override suspend fun search(query: String, page: Int, rows: Int): Flow<ApiResult<List<Content>>> =
        safeFlow {
            withContext(ioDispatcher) {
                val (start, end) = DateRange.todayToDaysAhead(365)
                val xml = kopisApiService.requestPerformanceSearch(
                    serviceKey = BuildConfig.KOPIS_SERVICE_KEY,
                    startDate = start,
                    endDate = end,
                    page = page,
                    rows = rows,
                    title = query,
                )
                KopisXmlParser.parsePerformanceList(xml)
            }
        }
}
