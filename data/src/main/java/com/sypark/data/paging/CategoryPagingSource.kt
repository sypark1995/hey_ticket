package com.sypark.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sypark.data.BuildConfig
import com.sypark.data.mapper.KopisGenreMapper
import com.sypark.data.mapper.KopisXmlParser
import com.sypark.data.service.KopisApiService
import com.sypark.data.util.DateRange
import com.sypark.domain.model.Content
import javax.inject.Inject

class CategoryPagingSource @Inject constructor(
    private val service: KopisApiService,
    private val genre: String,
    private val areaCode: String? = null,
    private val prfstate: String? = null,
    private val specificDate: String? = null,
) : PagingSource<Int, Content>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {
        return try {
            val nextPage = params.key ?: 1
            val (start, end) = if (specificDate != null) {
                specificDate to specificDate
            } else {
                DateRange.todayToDaysAhead(90)
            }
            val xml = service.requestPerformanceList(
                serviceKey = BuildConfig.KOPIS_SERVICE_KEY,
                startDate = start,
                endDate = end,
                page = nextPage,
                rows = params.loadSize,
                genreCode = KopisGenreMapper.toShcate(genre),
                areaCode = areaCode,
                prfstate = prfstate,
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
