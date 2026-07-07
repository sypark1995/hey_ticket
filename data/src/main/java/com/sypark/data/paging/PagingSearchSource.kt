package com.sypark.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sypark.data.BuildConfig
import com.sypark.data.mapper.KopisXmlParser
import com.sypark.data.service.KopisApiService
import com.sypark.data.util.DateRange
import com.sypark.data.util.Util
import com.sypark.domain.model.Content
import javax.inject.Inject

class PagingSearchSource @Inject constructor(
    private val service: KopisApiService,
    private val query: String,
    private val searchType: Util.SearchType? = Util.SearchType.PERFORMANCE
) : PagingSource<Int, Pair<Content, Long>>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pair<Content, Long>> {
        return try {
            val nextPage = params.key ?: 1
            val (start, end) = DateRange.todayToDaysAhead(365)
            val xml = service.requestPerformanceSearch(
                serviceKey = BuildConfig.KOPIS_SERVICE_KEY,
                startDate = start,
                endDate = end,
                page = nextPage,
                rows = params.loadSize,
                title = query,
            )
            val item = KopisXmlParser.parsePerformanceList(xml)

            LoadResult.Page(
                data = item.map { Pair(it, item.size.toLong()) },
                prevKey = null,
                nextKey = if (item.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pair<Content, Long>>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(20) ?: anchorPage?.nextKey?.minus(20)
        }
    }
}
