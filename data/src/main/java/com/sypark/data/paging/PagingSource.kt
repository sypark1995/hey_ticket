package com.sypark.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sypark.data.BuildConfig
import com.sypark.data.mapper.KopisGenreMapper
import com.sypark.data.mapper.KopisXmlParser
import com.sypark.data.service.KopisApiService
import com.sypark.data.util.DateRange
import com.sypark.data.util.Util
import com.sypark.domain.model.Content
import javax.inject.Inject

class PagingSource @Inject constructor(
    private val service: KopisApiService,
    private val boxOfficeGenre: String,
    private val timePeriod: Util.ButtonType? = Util.ButtonType.DAY
) : PagingSource<Int, Content>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {
        return try {
            val ststype = when (timePeriod) {
                Util.ButtonType.WEEK -> "week"
                Util.ButtonType.MONTH -> "month"
                else -> "day"
            }
            val xml = service.requestBoxOffice(
                serviceKey = BuildConfig.KOPIS_SERVICE_KEY,
                periodType = ststype,
                date = DateRange.yesterday(),
                genreCode = KopisGenreMapper.toCatecode(boxOfficeGenre),
            )
            val item = KopisXmlParser.parseBoxOffice(xml)

            LoadResult.Page(data = item, prevKey = null, nextKey = null)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Content>): Int? = null
}
