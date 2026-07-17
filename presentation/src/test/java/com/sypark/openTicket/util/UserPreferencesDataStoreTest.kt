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

    @Test
    fun `favorites default to empty and can be toggled on then off`() = runTest {
        val tempDir = kotlin.io.path.createTempDirectory().toFile()
        val store = newStore(tempDir)

        assertEquals(emptySet<String>(), store.getFavoriteIds())
        assertEquals(false, store.isFavorite("PF223939"))

        store.toggleFavorite("PF223939")

        assertEquals(true, store.isFavorite("PF223939"))
        assertEquals(setOf("PF223939"), store.getFavoriteIds())

        store.toggleFavorite("PF223939")

        assertEquals(false, store.isFavorite("PF223939"))
        assertEquals(emptySet<String>(), store.getFavoriteIds())
    }

    @Test
    fun `multiple favorites are stored independently`() = runTest {
        val tempDir = kotlin.io.path.createTempDirectory().toFile()
        val store = newStore(tempDir)

        store.toggleFavorite("PF223939")
        store.toggleFavorite("PF296190")

        assertEquals(setOf("PF223939", "PF296190"), store.getFavoriteIds())

        store.toggleFavorite("PF223939")

        assertEquals(setOf("PF296190"), store.getFavoriteIds())
    }

    @Test
    fun `recently viewed defaults to empty and records a new view`() = runTest {
        val tempDir = kotlin.io.path.createTempDirectory().toFile()
        val store = newStore(tempDir)

        assertEquals(emptyList<String>(), store.getRecentlyViewedIds())

        store.recordView("PF223939")

        assertEquals(listOf("PF223939"), store.getRecentlyViewedIds())
    }

    @Test
    fun `viewing an already-viewed performance moves it back to the front`() = runTest {
        val tempDir = kotlin.io.path.createTempDirectory().toFile()
        val store = newStore(tempDir)

        store.recordView("PF223939")
        store.recordView("PF296190")
        store.recordView("PF223939")

        assertEquals(listOf("PF223939", "PF296190"), store.getRecentlyViewedIds())
    }

    @Test
    fun `recently viewed list is capped at 20, dropping the oldest`() = runTest {
        val tempDir = kotlin.io.path.createTempDirectory().toFile()
        val store = newStore(tempDir)

        (1..21).forEach { store.recordView("PF$it") }

        val ids = store.getRecentlyViewedIds()
        assertEquals(20, ids.size)
        assertEquals("PF21", ids.first())
        assertEquals(false, ids.contains("PF1"))
    }
}
