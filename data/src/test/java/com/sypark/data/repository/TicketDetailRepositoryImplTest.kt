package com.sypark.data.repository

import com.sypark.data.service.KopisApiService
import com.sypark.domain.model.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

private const val PERFORMANCE_XML = """
<dbs>
  <db>
    <mt20id>PF223939</mt20id>
    <mt10id>FC000001</mt10id>
    <prfnm>위키드</prfnm>
    <fcltynm>블루스퀘어</fcltynm>
    <genrenm>뮤지컬</genrenm>
    <prfstate>공연중</prfstate>
  </db>
</dbs>
"""

private const val FACILITY_XML = """
<dbs>
  <db>
    <adres>서울특별시 용산구 이태원로 294</adres>
    <la>37.53987</la>
    <lo>126.99931</lo>
    <telno>02-1552-1369</telno>
  </db>
</dbs>
"""

// Named distinctly from the `FakeKopisApiService` in TicketRepositoryImplTest.kt (same package)
// to avoid a top-level class redeclaration clash.
private class FakeTicketDetailKopisApiService : KopisApiService {
    override suspend fun requestPerformanceList(serviceKey: String, startDate: String, endDate: String, page: Int, rows: Int, genreCode: String?, areaCode: String?, prfstate: String?) = PERFORMANCE_XML
    override suspend fun requestPerformanceSearch(serviceKey: String, startDate: String, endDate: String, page: Int, rows: Int, title: String) = PERFORMANCE_XML
    override suspend fun requestPerformanceDetail(performanceId: String, serviceKey: String) = PERFORMANCE_XML
    override suspend fun requestFacilityDetail(venueId: String, serviceKey: String) = FACILITY_XML
}

class TicketDetailRepositoryImplTest {

    @Test
    fun `getTicketDetail merges performance and facility responses`() = runTest {
        val repository = TicketDetailRepositoryImpl(FakeTicketDetailKopisApiService(), Dispatchers.Unconfined)

        repository.getTicketDetail("PF223939").collect { result ->
            assertTrue(result is ApiResult.Success)
            val detail = (result as ApiResult.Success).value
            assertEquals("위키드", detail.title)
            assertEquals("서울특별시 용산구 이태원로 294", detail.address)
            assertEquals(37.53987, detail.latitude, 0.00001)
        }
    }
}
