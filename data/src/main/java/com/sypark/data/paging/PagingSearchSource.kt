package com.sypark.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sypark.data.db.entity.Content
import com.sypark.data.db.entity.Data
import com.sypark.data.service.OpenTicketService
import com.sypark.data.util.Util
import javax.inject.Inject

class PagingSearchSource @Inject constructor(
    private val service: OpenTicketService,
    private val query: String,
    private val searchType: Util.SearchType? = Util.SearchType.PERFORMANCE
) : PagingSource<Int, Pair<Content,Long>>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pair<Content,Long>> {
        return try {

            val nextPage = params.key ?: 1

            val item = Gson().fromJson<Data>(
                service.requestSearch(
                    query = query,
                    searchType = searchType!!.name,
                    page = nextPage,
                    pageSize = params.loadSize,
                ).data, object : TypeToken<Data>() {}.type
            )

//            LoadResult.Page(
//                data = item.contents,
//                prevKey = null,
//                nextKey = if (item.contents.isEmpty()) null else nextPage + 1
//            )

            LoadResult.Page(
                data = item.contents.map {
                    Pair(it,item.pageSize)
                },
                prevKey = null,
                nextKey = if (item.contents.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pair<Content,Long>>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(20) ?: anchorPage?.nextKey?.minus(20)
        }
    }
}