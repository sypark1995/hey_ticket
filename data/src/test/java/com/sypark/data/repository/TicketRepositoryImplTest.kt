package com.sypark.data.repository

import com.sypark.data.service.KopisApiService
import com.sypark.domain.model.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

private const val LIST_XML = """
<dbs>
  <db>
    <mt20id>PF223939</mt20id>
    <prfnm>위키드</prfnm>
    <fcltynm>블루스퀘어</fcltynm>
    <genrenm>뮤지컬</genrenm>
    <openrun>N</openrun>
    <prfstate>공연중</prfstate>
  </db>
</dbs>
"""

class FakeKopisApiService : KopisApiService {
    var lastRequestedGenreCode: String? = null

    override suspend fun requestPerformanceList(
        serviceKey: String, startDate: String, endDate: String, page: Int, rows: Int, genreCode: String?, areaCode: String?, prfstate: String?
    ): String {
        lastRequestedGenreCode = genreCode
        return LIST_XML
    }

    override suspend fun requestPerformanceSearch(
        serviceKey: String, startDate: String, endDate: String, page: Int, rows: Int, title: String
    ) = LIST_XML

    override suspend fun requestPerformanceDetail(performanceId: String, serviceKey: String) = LIST_XML

    override suspend fun requestFacilityDetail(venueId: String, serviceKey: String) = LIST_XML
}

class TicketRepositoryImplTest {

    @Test
    fun `getPerformanceList parses fake xml into Success`() = runTest {
        val repository = TicketRepositoryImpl(FakeKopisApiService(), Dispatchers.Unconfined)

        repository.getPerformanceList(null, 1, 20).collect { result ->
            assertTrue(result is ApiResult.Success)
            val list = (result as ApiResult.Success).value
            assertEquals(1, list.size)
            assertEquals("위키드", list[0].title)
        }
    }

    @Test
    fun `getNew maps the app genre code to the KOPIS shcate before requesting`() = runTest {
        val fakeService = FakeKopisApiService()
        val repository = TicketRepositoryImpl(fakeService, Dispatchers.Unconfined)

        repository.getNew("MUSICAL", 1, 10).collect {}

        assertEquals("GGGA", fakeService.lastRequestedGenreCode)
    }

    @Test
    fun `getNew requests no genre filter when given an unmapped code`() = runTest {
        val fakeService = FakeKopisApiService()
        val repository = TicketRepositoryImpl(fakeService, Dispatchers.Unconfined)

        repository.getNew("", 1, 10).collect {}

        assertEquals(null, fakeService.lastRequestedGenreCode)
    }
}
