package com.sypark.data.repository

import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.Content
import com.sypark.domain.repository.TicketRepository
import kotlinx.coroutines.Dispatchers
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
            override suspend fun getRanking(periodType: String, genreCode: String?, areaCode: String?) = flowOf(ApiResult.Success(emptyList<Content>()))
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
}
