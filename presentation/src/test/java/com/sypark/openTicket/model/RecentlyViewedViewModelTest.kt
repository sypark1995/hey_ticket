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

class RecentlyViewedViewModelTest {

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
    fun `loadRecentlyViewed preserves stored order and drops failures`() = runTest {
        val dataStore = newDataStore()
        dataStore.recordView("OK_OLD")
        dataStore.recordView("FAIL1")
        dataStore.recordView("OK_NEW")
        // stored order is now: OK_NEW, FAIL1, OK_OLD (most recent first)

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

        val viewModel = RecentlyViewedViewModel(GetPerformanceDetailUseCase(repository), dataStore)
        viewModel.loadRecentlyViewed()

        val result = viewModel.recentlyViewed.value!!
        assertEquals(listOf("OK_NEW", "OK_OLD"), result.map { it.id })
    }
}
