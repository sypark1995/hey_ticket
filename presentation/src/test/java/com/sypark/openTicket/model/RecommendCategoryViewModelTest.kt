package com.sypark.openTicket.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.sypark.openTicket.util.UserPreferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.io.File

class RecommendCategoryViewModelTest {

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
    fun `loadSavedSelections exposes previously saved genres and areas`() = runTest {
        val dataStore = newDataStore()
        dataStore.setInterestedGenres(setOf("MUSICAL"))
        dataStore.setInterestedAreas(setOf("SEOUL", "BUSAN"))

        val viewModel = RecommendCategoryViewModel(dataStore)
        viewModel.loadSavedSelections()

        assertEquals(setOf("MUSICAL"), viewModel.savedGenres.value)
        assertEquals(setOf("SEOUL", "BUSAN"), viewModel.savedAreas.value)
    }

    @Test
    fun `saveSelections persists the given genres and areas`() = runTest {
        val dataStore = newDataStore()
        val viewModel = RecommendCategoryViewModel(dataStore)

        viewModel.saveSelections(setOf("THEATER", "CLASSIC"), setOf("DAEGU"))

        assertEquals(setOf("THEATER", "CLASSIC"), dataStore.getInterestedGenres())
        assertEquals(setOf("DAEGU"), dataStore.getInterestedAreas())
    }
}
