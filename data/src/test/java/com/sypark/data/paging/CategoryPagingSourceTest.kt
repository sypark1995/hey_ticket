package com.sypark.data.paging

import androidx.paging.PagingSource
import com.sypark.data.service.KopisApiService
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

class CategoryPagingSourceTest {

    @Test
    fun `load maps genre to shcate and requests the search endpoint`() = runTest {
        var capturedGenreCode: String? = "unset"
        val fakeService = object : KopisApiService {
            override suspend fun requestPerformanceList(serviceKey: String, startDate: String, endDate: String, page: Int, rows: Int, genreCode: String?): String {
                capturedGenreCode = genreCode
                return LIST_XML
            }
            override suspend fun requestPerformanceSearch(serviceKey: String, startDate: String, endDate: String, page: Int, rows: Int, title: String) = LIST_XML
            override suspend fun requestPerformanceDetail(performanceId: String, serviceKey: String) = LIST_XML
            override suspend fun requestFacilityDetail(venueId: String, serviceKey: String) = LIST_XML
        }
        val source = CategoryPagingSource(fakeService, genre = "MUSICAL")

        val result = source.load(
            PagingSource.LoadParams.Refresh(key = null, loadSize = 20, placeholdersEnabled = false)
        )

        assertTrue(result is PagingSource.LoadResult.Page)
        assertEquals("GGGA", capturedGenreCode)
    }
}
