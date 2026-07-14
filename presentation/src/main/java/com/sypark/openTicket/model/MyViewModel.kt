package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.openTicket.base.BaseViewModel
import com.sypark.openTicket.util.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore
) : BaseViewModel() {

    private val _nickname = MutableLiveData<String?>()
    val nickname: LiveData<String?> = _nickname

    private val _profileImageUrl = MutableLiveData<String?>()
    val profileImageUrl: LiveData<String?> = _profileImageUrl

    suspend fun loadProfile() {
        _nickname.value = userPreferencesDataStore.getKakaoNickname()
        _profileImageUrl.value = userPreferencesDataStore.getKakaoProfileImageUrl()
    }

    suspend fun saveProfile(nickname: String, profileImageUrl: String) {
        userPreferencesDataStore.setKakaoProfile(nickname, profileImageUrl)
        _nickname.value = nickname
        _profileImageUrl.value = profileImageUrl
    }

    suspend fun clearProfile() {
        userPreferencesDataStore.setKakaoProfile("", "")
        _nickname.value = null
        _profileImageUrl.value = null
    }
}
