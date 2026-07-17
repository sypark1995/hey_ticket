package com.sypark.domain.repository

import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.Content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class TicketRepositoryContractTest {

    private val fakeContent = Content(
        id = "PF223939", placeId = "FC000001", title = "위키드",
        startDate = "2026.07.01", endDate = "2026.09.30", theater = "블루스퀘어",
        cast = "", crew = "", runtime = "", age = "", company = "", price = "",
        poster = "", story = "", genre = "뮤지컬", state = "공연중", openRun = false,
        storyUrls = emptyList(), schedule = "", rank = 0,
    )

    private val fake = object : TicketRepository {
        override suspend fun getPerformanceList(genreCode: String?, page: Int, rows: Int): Flow<ApiResult<List<Content>>> =
            flowOf(ApiResult.Success(listOf(fakeContent)))

        override suspend fun getNew(genreCode: String?, page: Int, rows: Int): Flow<ApiResult<List<Content>>> =
            flowOf(ApiResult.Success(listOf(fakeContent)))

        override suspend fun getDetail(performanceId: String): Flow<ApiResult<Content>> =
            flowOf(ApiResult.Success(fakeContent))

        override suspend fun search(query: String, page: Int, rows: Int): Flow<ApiResult<List<Content>>> =
            flowOf(ApiResult.Success(listOf(fakeContent)))

        override suspend fun getClosingSoon(rows: Int): Flow<ApiResult<List<Content>>> =
            flowOf(ApiResult.Success(listOf(fakeContent)))
    }

    @Test
    fun `fake repository returns expected content`() = runBlocking {
        val result = fake.getDetail("PF223939")
        result.collect { r ->
            assertEquals(ApiResult.Success(fakeContent), r)
        }
    }
}
