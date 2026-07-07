package com.sypark.data.repository

import com.sypark.data.AppDispatchers
import com.sypark.data.BuildConfig
import com.sypark.data.Dispatcher
import com.sypark.data.db.entity.safeFlow
import com.sypark.data.mapper.KopisXmlParser
import com.sypark.data.service.KopisApiService
import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.TicketDetail
import com.sypark.domain.repository.TicketDetailRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TicketDetailRepositoryImpl @Inject constructor(
    private val kopisApiService: KopisApiService,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : TicketDetailRepository {
    override suspend fun getTicketDetail(id: String): Flow<ApiResult<TicketDetail>> =
        safeFlow {
            withContext(ioDispatcher) {
                val performanceXml = kopisApiService.requestPerformanceDetail(id, BuildConfig.KOPIS_SERVICE_KEY)
                val performance = KopisXmlParser.parsePerformanceDetail(performanceXml)
                val facilityXml = kopisApiService.requestFacilityDetail(performance.placeId, BuildConfig.KOPIS_SERVICE_KEY)
                val facility = KopisXmlParser.parseFacility(facilityXml)
                KopisXmlParser.toTicketDetail(performance, facility)
            }
        }
}
