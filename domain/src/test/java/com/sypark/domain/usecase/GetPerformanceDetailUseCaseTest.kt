package com.sypark.domain.usecase

import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.Content
import com.sypark.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetPerformanceDetailUseCaseTest {

    private val expected = Content(
        id = "PF223939", placeId = "FC000001", title = "위키드",
        startDate = "2026.07.01", endDate = "2026.09.30", theater = "블루스퀘어",
        cast = "", crew = "", runtime = "", age = "", company = "", price = "",
        poster = "", story = "", genre = "뮤지컬", state = "공연중", openRun = false,
        storyUrls = emptyList(), schedule = "", rank = 0,
    )

    private val repository = object : TicketRepository {
        override suspend fun getPerformanceList(genreCode: String?, page: Int, rows: Int) = flowOf(ApiResult.Success(listOf(expected)))
        override suspend fun getNew(genreCode: String?, page: Int, rows: Int) = flowOf(ApiResult.Success(listOf(expected)))
        override suspend fun getDetail(performanceId: String): Flow<ApiResult<Content>> = flowOf(ApiResult.Success(expected))
        override suspend fun search(query: String, page: Int, rows: Int) = flowOf(ApiResult.Success(listOf(expected)))
        override suspend fun getClosingSoon(rows: Int) = flowOf(ApiResult.Success(listOf(expected)))
        override suspend fun getMatchingNew(genreCode: String?, areaCode: String?, rows: Int): Flow<ApiResult<List<Content>>> = flowOf(ApiResult.Success(emptyList()))
    }

    @Test
    fun `invoke delegates to repository getDetail`() = runTest {
        val useCase = GetPerformanceDetailUseCase(repository)
        useCase("PF223939").collect { result ->
            assertEquals(ApiResult.Success(expected), result)
        }
    }
}
