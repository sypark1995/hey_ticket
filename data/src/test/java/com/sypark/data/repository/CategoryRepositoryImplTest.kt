package com.sypark.data.repository

import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.Content
import com.sypark.domain.repository.TicketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CategoryRepositoryImplTest {

    private fun contentOf(genre: String) = Content(
        id = "", placeId = "", title = "", startDate = "", endDate = "", theater = "",
        cast = "", crew = "", runtime = "", age = "", company = "", price = "", poster = "",
        story = "", genre = genre, state = "", openRun = false, storyUrls = emptyList(),
        schedule = "", rank = 0,
    )

    @Test
    fun `getGenreCountList counts performances per mapped genre code`() = runTest {
        val ticketRepository = object : TicketRepository {
            override suspend fun getPerformanceList(genreCode: String?, page: Int, rows: Int) =
                flowOf(ApiResult.Success(List(3) { contentOf("연극") }))
            override suspend fun getNew(genreCode: String?, page: Int, rows: Int) = flowOf(ApiResult.Success(emptyList<Content>()))
            override suspend fun getDetail(performanceId: String) = flowOf(ApiResult.Error())
            override suspend fun search(query: String, page: Int, rows: Int) = flowOf(ApiResult.Success(emptyList<Content>()))
        }
        val repository = CategoryRepositoryImpl(ticketRepository, Dispatchers.Unconfined)

        repository.getGenreCountList(listOf("THEATER")).collect { result ->
            assertTrue(result is ApiResult.Success)
            val list = (result as ApiResult.Success).value
            assertEquals("THEATER", list[0].genre)
            assertEquals(3, list[0].count)
        }
    }

    @Test
    fun `getGenreCountList preserves input order across concurrently fetched genre codes`() = runTest {
        val ticketRepository = object : TicketRepository {
            override suspend fun getPerformanceList(genreCode: String?, page: Int, rows: Int): Flow<ApiResult<List<Content>>> {
                val size = when (genreCode) {
                    "AAAA" -> 1
                    "CCCD" -> 2
                    else -> 5
                }
                return flowOf(ApiResult.Success(List(size) { contentOf("x") }))
            }
            override suspend fun getNew(genreCode: String?, page: Int, rows: Int) = flowOf(ApiResult.Success(emptyList<Content>()))
            override suspend fun getDetail(performanceId: String) = flowOf(ApiResult.Error())
            override suspend fun search(query: String, page: Int, rows: Int) = flowOf(ApiResult.Success(emptyList<Content>()))
        }
        val repository = CategoryRepositoryImpl(ticketRepository, Dispatchers.Unconfined)

        repository.getGenreCountList(listOf("THEATER", "POPULAR_MUSIC", "ALL")).collect { result ->
            assertTrue(result is ApiResult.Success)
            val list = (result as ApiResult.Success).value
            assertEquals(listOf("THEATER", "POPULAR_MUSIC", "ALL"), list.map { it.genre })
            assertEquals(listOf(1, 2, 5), list.map { it.count })
        }
    }
}
