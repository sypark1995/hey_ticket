package com.sypark.openTicket.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.Content
import com.sypark.domain.repository.TicketRepository
import com.sypark.domain.usecase.GetPerformanceDetailUseCase
import com.sypark.openTicket.util.UserPreferencesDataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.io.File

private fun content(id: String, title: String) = Content(
    id = id, placeId = "", title = title, startDate = "", endDate = "", theater = "",
    cast = "", crew = "", runtime = "", age = "", company = "", price = "", poster = "",
    story = "", genre = "", state = "", openRun = false, storyUrls = emptyList(),
    schedule = "", rank = 0,
)

class FavoritesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private fun newDataStore(): UserPreferencesDataStore {
        val tempDir = kotlin.io.path.createTempDirectory().toFile()
        val dataStore = PreferenceDataStoreFactory.create(
            scope = kotlinx.coroutines.CoroutineScope(Dispatchers.Unconfined),
        ) { File(tempDir, "test_user_prefs.preferences_pb") }
        return UserPreferencesDataStore(dataStore)
    }

    @Test
    fun `loadFavorites collects successful lookups and drops failures`() = runTest {
        val dataStore = newDataStore()
        dataStore.toggleFavorite("OK1")
        dataStore.toggleFavorite("FAIL1")
        dataStore.toggleFavorite("OK2")

        val repository = object : TicketRepository {
            override suspend fun getPerformanceList(genreCode: String?, page: Int, rows: Int): Flow<ApiResult<List<Content>>> = flowOf(ApiResult.Success(emptyList()))
            override suspend fun getNew(genreCode: String?, page: Int, rows: Int): Flow<ApiResult<List<Content>>> = flowOf(ApiResult.Success(emptyList()))
            override suspend fun search(query: String, page: Int, rows: Int): Flow<ApiResult<List<Content>>> = flowOf(ApiResult.Success(emptyList()))
            override suspend fun getClosingSoon(rows: Int): Flow<ApiResult<List<Content>>> = flowOf(ApiResult.Success(emptyList()))
            override suspend fun getMatchingNew(genreCode: String?, areaCode: String?, rows: Int): Flow<ApiResult<List<Content>>> = flowOf(ApiResult.Success(emptyList()))
            override suspend fun getDetail(performanceId: String): Flow<ApiResult<Content>> =
                if (performanceId == "FAIL1") {
                    flowOf(ApiResult.Error())
                } else {
                    flowOf(ApiResult.Success(content(performanceId, "제목-$performanceId")))
                }
        }

        val viewModel = FavoritesViewModel(GetPerformanceDetailUseCase(repository), dataStore)
        viewModel.loadFavorites()

        val result = viewModel.favorites.value!!
        assertEquals(2, result.size)
        assertEquals(setOf("OK1", "OK2"), result.map { it.id }.toSet())
    }

    @Test
    fun `removeFavorite unfavorites and removes from the current list`() = runTest {
        val dataStore = newDataStore()
        dataStore.toggleFavorite("OK1")
        dataStore.toggleFavorite("OK2")

        val repository = object : TicketRepository {
            override suspend fun getPerformanceList(genreCode: String?, page: Int, rows: Int): Flow<ApiResult<List<Content>>> = flowOf(ApiResult.Success(emptyList()))
            override suspend fun getNew(genreCode: String?, page: Int, rows: Int): Flow<ApiResult<List<Content>>> = flowOf(ApiResult.Success(emptyList()))
            override suspend fun search(query: String, page: Int, rows: Int): Flow<ApiResult<List<Content>>> = flowOf(ApiResult.Success(emptyList()))
            override suspend fun getClosingSoon(rows: Int): Flow<ApiResult<List<Content>>> = flowOf(ApiResult.Success(emptyList()))
            override suspend fun getMatchingNew(genreCode: String?, areaCode: String?, rows: Int): Flow<ApiResult<List<Content>>> = flowOf(ApiResult.Success(emptyList()))
            override suspend fun getDetail(performanceId: String): Flow<ApiResult<Content>> =
                flowOf(ApiResult.Success(content(performanceId, "제목-$performanceId")))
        }

        val viewModel = FavoritesViewModel(GetPerformanceDetailUseCase(repository), dataStore)
        viewModel.loadFavorites()

        viewModel.removeFavorite("OK1")

        assertEquals(listOf("OK2"), viewModel.favorites.value!!.map { it.id })
        assertEquals(setOf("OK2"), dataStore.getFavoriteIds())
    }
}
