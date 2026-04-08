package com.sypark.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sypark.data.db.entity.Content
import com.sypark.data.db.entity.Data
import com.sypark.data.service.OpenTicketService
import kotlinx.coroutines.delay
import javax.inject.Inject

class PagingSource @Inject constructor(
    private val service: OpenTicketService,
    private val genre: String
) : PagingSource<Int, Content>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {
        return try {
            delay(1000)
            val next = params.key ?: 1
            val item = Gson().fromJson<Data>(
                service.requestPerformancesRanking(
                    timePeriod = "",
                    date = "",
                    genre = genre,
                    area = "",
                    page = next,
                    pageSize = params.loadSize,
                ).data, object : TypeToken<Data>() {}.type
            ).contents

            LoadResult.Page(
                data = item,
                prevKey = if (next == 1) null else next - 1,
                nextKey = next + 1
            )
        } catch (e: Exception) {
            Log.e("!!!!!", e.toString())
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Content>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}