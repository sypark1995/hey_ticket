package com.sypark.data.repository
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sypark.data.db.entity.Ticket
import com.sypark.data.service.OpenTicketService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PagingRepository @Inject constructor(
    private val service: OpenTicketService
) {
    fun getPagingData(): Flow<PagingData<Ticket>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { PagingSource(service) }
        ).flow
    }
}