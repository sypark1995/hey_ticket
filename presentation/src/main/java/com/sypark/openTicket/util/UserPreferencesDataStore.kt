package com.sypark.openTicket.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

@Singleton
class UserPreferencesDataStore(
    private val dataStore: DataStore<Preferences>
) {
    @Inject
    constructor(@ApplicationContext context: Context) : this(context.userPreferencesDataStore)

    private object Keys {
        val SORT_POSITION = intPreferencesKey("sort_position")
        val PRICE = stringPreferencesKey("price")
        val KAKAO_NICKNAME = stringPreferencesKey("kakao_nickname")
        val KAKAO_PROFILE_IMAGE_URL = stringPreferencesKey("kakao_profile_image_url")
    }

    suspend fun getSortPosition(): Int = dataStore.data.first()[Keys.SORT_POSITION] ?: 1
    suspend fun setSortPosition(value: Int) {
        dataStore.edit { it[Keys.SORT_POSITION] = value }
    }

    suspend fun getPrice(): String = dataStore.data.first()[Keys.PRICE] ?: ""
    suspend fun setPrice(value: String) {
        dataStore.edit { it[Keys.PRICE] = value }
    }

    suspend fun getKakaoNickname(): String? = dataStore.data.first()[Keys.KAKAO_NICKNAME]
    suspend fun getKakaoProfileImageUrl(): String? = dataStore.data.first()[Keys.KAKAO_PROFILE_IMAGE_URL]

    suspend fun setKakaoProfile(nickname: String, profileImageUrl: String) {
        dataStore.edit {
            it[Keys.KAKAO_NICKNAME] = nickname
            it[Keys.KAKAO_PROFILE_IMAGE_URL] = profileImageUrl
        }
    }
}
