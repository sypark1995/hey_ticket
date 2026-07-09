package com.sypark.openTicket.util

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.io.File

class UserPreferencesDataStoreTest {

    private fun newStore(tempDir: File): UserPreferencesDataStore {
        val dataStore = PreferenceDataStoreFactory.create(
            scope = kotlinx.coroutines.CoroutineScope(Dispatchers.Unconfined),
        ) { File(tempDir, "test_user_prefs.preferences_pb") }
        return UserPreferencesDataStore(dataStore)
    }

    @Test
    fun `sortPosition defaults to 1 and can be set`() = runTest {
        val tempDir = kotlin.io.path.createTempDirectory().toFile()
        val store = newStore(tempDir)

        assertEquals(1, store.getSortPosition())

        store.setSortPosition(3)

        assertEquals(3, store.getSortPosition())
    }

    @Test
    fun `price defaults to empty string and can be set`() = runTest {
        val tempDir = kotlin.io.path.createTempDirectory().toFile()
        val store = newStore(tempDir)

        assertEquals("", store.getPrice())

        store.setPrice("R석 160,000원")

        assertEquals("R석 160,000원", store.getPrice())
    }

    @Test
    fun `kakao profile defaults to null and can be set together`() = runTest {
        val tempDir = kotlin.io.path.createTempDirectory().toFile()
        val store = newStore(tempDir)

        assertNull(store.getKakaoNickname())
        assertNull(store.getKakaoProfileImageUrl())

        store.setKakaoProfile("홍길동", "https://example.com/profile.jpg")

        assertEquals("홍길동", store.getKakaoNickname())
        assertEquals("https://example.com/profile.jpg", store.getKakaoProfileImageUrl())
    }
}
