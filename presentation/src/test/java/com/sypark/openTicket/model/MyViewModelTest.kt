package com.sypark.openTicket.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.sypark.openTicket.util.UserPreferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import java.io.File

class MyViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private fun newStoreAndViewModel(): Pair<UserPreferencesDataStore, MyViewModel> {
        val tempDir = kotlin.io.path.createTempDirectory().toFile()
        val dataStore = PreferenceDataStoreFactory.create(
            scope = CoroutineScope(Dispatchers.Unconfined),
        ) { File(tempDir, "test_user_prefs.preferences_pb") }
        val store = UserPreferencesDataStore(dataStore)
        return store to MyViewModel(store)
    }

    @Test
    fun `loadProfile exposes stored nickname and profile image`() = runTest {
        val (store, viewModel) = newStoreAndViewModel()
        store.setKakaoProfile("홍길동", "https://example.com/profile.jpg")

        viewModel.loadProfile()

        assertEquals("홍길동", viewModel.nickname.value)
        assertEquals("https://example.com/profile.jpg", viewModel.profileImageUrl.value)
    }

    @Test
    fun `saveProfile writes to the datastore and updates the exposed LiveData`() = runTest {
        val (store, viewModel) = newStoreAndViewModel()

        viewModel.saveProfile("김철수", "https://example.com/other.jpg")

        assertEquals("김철수", viewModel.nickname.value)
        assertEquals("https://example.com/other.jpg", viewModel.profileImageUrl.value)
        assertEquals("김철수", store.getKakaoNickname())
        assertEquals("https://example.com/other.jpg", store.getKakaoProfileImageUrl())
    }

    @Test
    fun `clearProfile clears both the datastore and the exposed LiveData`() = runTest {
        val (store, viewModel) = newStoreAndViewModel()
        store.setKakaoProfile("홍길동", "https://example.com/profile.jpg")
        viewModel.loadProfile()

        viewModel.clearProfile()

        assertNull(viewModel.nickname.value)
        assertNull(viewModel.profileImageUrl.value)
        assertEquals("", store.getKakaoNickname())
        assertEquals("", store.getKakaoProfileImageUrl())
    }
}
