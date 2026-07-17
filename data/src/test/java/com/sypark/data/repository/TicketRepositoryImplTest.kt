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

private const val CLOSING_SOON_XML = """
<dbs>
  <db>
    <mt20id>PF000001</mt20id>
    <prfnm>가장 늦게 끝나는 공연</prfnm>
    <fcltynm>극장A</fcltynm>
    <prfpdfrom>2026.07.01</prfpdfrom>
    <prfpdto>2026.09.30</prfpdto>
    <genrenm>연극</genrenm>
    <openrun>N</openrun>
    <prfstate>공연중</prfstate>
  </db>
  <db>
    <mt20id>PF000002</mt20id>
    <prfnm>가장 먼저 끝나는 공연</prfnm>
    <fcltynm>극장B</fcltynm>
    <prfpdfrom>2026.07.01</prfpdfrom>
    <prfpdto>2026.07.20</prfpdto>
    <genrenm>연극</genrenm>
    <openrun>N</openrun>
    <prfstate>공연중</prfstate>
  </db>
  <db>
    <mt20id>PF000003</mt20id>
    <prfnm>중간에 끝나는 공연</prfnm>
    <fcltynm>극장C</fcltynm>
    <prfpdfrom>2026.07.01</prfpdfrom>
    <prfpdto>2026.08.15</prfpdto>
    <genrenm>연극</genrenm>
    <openrun>N</openrun>
    <prfstate>공연중</prfstate>
  </db>
</dbs>
"""

class FakeKopisApiService : KopisApiService {
    var lastRequestedGenreCode: String? = null
    var lastRequestedPrfstate: String? = null
    var responseXml: String = LIST_XML

    override suspend fun requestPerformanceList(
        serviceKey: String, startDate: String, endDate: String, page: Int, rows: Int, genreCode: String?, areaCode: String?, prfstate: String?
    ): String {
        lastRequestedGenreCode = genreCode
        lastRequestedPrfstate = prfstate
        return responseXml
    }

    override suspend fun requestPerformanceSearch(
        serviceKey: String, startDate: String, endDate: String, page: Int, rows: Int, title: String
    ) = responseXml

    override suspend fun requestPerformanceDetail(performanceId: String, serviceKey: String) = responseXml

    override suspend fun requestFacilityDetail(venueId: String, serviceKey: String) = responseXml
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

    @Test
    fun `getClosingSoon requests ongoing performances only`() = runTest {
        val fakeService = FakeKopisApiService()
        val repository = TicketRepositoryImpl(fakeService, Dispatchers.Unconfined)

        repository.getClosingSoon(10).collect {}

        assertEquals("01", fakeService.lastRequestedPrfstate)
    }

    @Test
    fun `getClosingSoon sorts results by endDate ascending and caps at rows`() = runTest {
        val fakeService = FakeKopisApiService().apply { responseXml = CLOSING_SOON_XML }
        val repository = TicketRepositoryImpl(fakeService, Dispatchers.Unconfined)

        repository.getClosingSoon(2).collect { result ->
            assertTrue(result is ApiResult.Success)
            val list = (result as ApiResult.Success).value
            assertEquals(2, list.size)
            assertEquals("가장 먼저 끝나는 공연", list[0].title)
            assertEquals("중간에 끝나는 공연", list[1].title)
        }
    }
}
