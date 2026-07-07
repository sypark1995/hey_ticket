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

class NewPagingSource @Inject constructor(
    private val service: KopisApiService,
    private val genre: String,
    private val sortType: Util.NewButtonType? = Util.NewButtonType.CREATED_DATE
) : PagingSource<Int, Content>() {
    // KOPIS 목록 API는 조회수순 정렬을 지원하지 않아 sortType과 무관하게 동일하게 호출한다 (Global Constraints 참고)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {
        return try {
            val nextPage = params.key ?: 1
            val (start, end) = DateRange.todayToDaysAhead(30)
            val xml = service.requestPerformanceList(
                serviceKey = BuildConfig.KOPIS_SERVICE_KEY,
                startDate = start,
                endDate = end,
                page = nextPage,
                rows = params.loadSize,
                genreCode = genre.ifBlank { null },
            )
            val item = KopisXmlParser.parsePerformanceList(xml)

            LoadResult.Page(
                data = item,
                prevKey = null,
                nextKey = if (item.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Content>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
