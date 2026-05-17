package com.sypark.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sypark.data.db.entity.Content
import com.sypark.data.service.OpenTicketService
import com.sypark.data.util.Util
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PagingRepository @Inject constructor(
    private val service: OpenTicketService
) {
    fun getPagingData(
        boxOfficeGenre: String,
        timePeriod: Util.ButtonType
    ): Flow<PagingData<Content>> {
        return Pager(
            config = PagingConfig(pageSize = PAGER_SIZE, initialLoadSize = PAGER_SIZE),
            pagingSourceFactory = { PagingSource(service, boxOfficeGenre, timePeriod) }
        ).flow
    }

    fun getNewPagingData(genre: String, sortType: Util.NewButtonType): Flow<PagingData<Content>> {
        return Pager(
            config = PagingConfig(pageSize = PAGER_SIZE, initialLoadSize = PAGER_SIZE),
            pagingSourceFactory = { NewPagingSource(service, genre, sortType) }
        ).flow
    }

//    fun getSearchPagingData(query: String, searchType: Util.SearchType): Flow<PagingData<Content>> {
//        return Pager(
//            config = PagingConfig(pageSize = PAGER_SIZE, initialLoadSize = PAGER_SIZE),
//            pagingSourceFactory = { PagingSearchSource(service, query, searchType) }
//        ).flow
//    }

    fun getSearchPagingData2(query: String, searchType: Util.SearchType): Flow<PagingData<Pair<Content,Long>>> {
        return Pager(
            config = PagingConfig(pageSize = PAGER_SIZE, initialLoadSize = PAGER_SIZE),
            pagingSourceFactory = { PagingSearchSource(service, query, searchType) }
        ).flow
    }

    companion object {
        const val PAGER_SIZE = 10
    }
}

