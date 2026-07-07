package com.sypark.data.repository

import com.sypark.data.AppDispatchers
import com.sypark.data.Dispatcher
import com.sypark.data.db.entity.safeFlow
import com.sypark.data.mapper.KopisGenreMapper
import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.GenreCount
import com.sypark.domain.repository.CategoryRepository
import com.sypark.domain.repository.TicketRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val ticketRepository: TicketRepository,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : CategoryRepository {
    override suspend fun getGenreCountList(genreCodes: List<String>): Flow<ApiResult<List<GenreCount>>> =
        safeFlow {
            withContext(ioDispatcher) {
                genreCodes.map { appGenreCode ->
                    var count = 0
                    ticketRepository.getPerformanceList(
                        KopisGenreMapper.toKopisCode(appGenreCode), page = 1, rows = 100
                    ).collect { result ->
                        if (result is ApiResult.Success) count = result.value.size
                    }
                    GenreCount(appGenreCode, count)
                }
            }
        }
}
