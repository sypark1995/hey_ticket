package com.sypark.data.paging

import androidx.paging.PagingSource
import com.sypark.data.service.KopisApiService
import com.sypark.data.util.Util
import kotlinx.coroutines.test.runTest
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

private class FakeKopisApiService : KopisApiService {
    override suspend fun requestPerformanceList(serviceKey: String, startDate: String, endDate: String, page: Int, rows: Int, genreCode: String?, areaCode: String?, prfstate: String?) = LIST_XML
    override suspend fun requestPerformanceSearch(serviceKey: String, startDate: String, endDate: String, page: Int, rows: Int, title: String) = LIST_XML
    override suspend fun requestPerformanceDetail(performanceId: String, serviceKey: String) = LIST_XML
    override suspend fun requestFacilityDetail(venueId: String, serviceKey: String) = LIST_XML
}

class NewPagingSourceTest {

    @Test
    fun `load returns a page with parsed content`() = runTest {
        val source = NewPagingSource(FakeKopisApiService(), genre = "AAAA", sortType = Util.NewButtonType.CREATED_DATE)

        val result = source.load(
            PagingSource.LoadParams.Refresh(key = null, loadSize = 20, placeholdersEnabled = false)
        )

        assertTrue(result is PagingSource.LoadResult.Page)
        val page = result as PagingSource.LoadResult.Page
        assertTrue(page.data.isNotEmpty())
        assertTrue(page.data[0].title == "위키드")
    }
}
