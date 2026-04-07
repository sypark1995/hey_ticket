package com.sypark.data.repository

import com.sypark.data.service.OpenTicketClient
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val openTicketClient: OpenTicketClient,
) : CategoryRepository {
    override suspend fun getGenreCountList() = flow {
        emit(openTicketClient.requestPerformanceCount())
    }
}