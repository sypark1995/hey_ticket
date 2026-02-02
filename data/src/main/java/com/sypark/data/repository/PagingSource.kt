package com.sypark.data.repository

import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sypark.data.db.entity.Ticket
import com.sypark.data.service.OpenTicketService
import kotlinx.coroutines.delay
import javax.inject.Inject

class PagingSource @Inject constructor(
    private val service: OpenTicketService
) : PagingSource<Int, Ticket>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Ticket> {
        return try {
            delay(1000)
            val next = params.key ?: 0
            val item = service.requestPerformances(next, params.loadSize)

            LoadResult.Page(
                data = item,
                prevKey = if (next == 0) null else next - 1,
                nextKey = next + 1
            )
        } catch (e: Exception) {
            Log.e("!!!!!",e.toString())
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Ticket>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}