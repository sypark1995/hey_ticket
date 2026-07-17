package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.openTicket.base.BaseViewModel
import com.sypark.openTicket.util.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendCategoryViewModel @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore,
) : BaseViewModel() {

    private val _savedGenres = MutableLiveData<Set<String>>()
    val savedGenres: LiveData<Set<String>> = _savedGenres

    private val _savedAreas = MutableLiveData<Set<String>>()
    val savedAreas: LiveData<Set<String>> = _savedAreas

    suspend fun loadSavedSelections() {
        _savedGenres.value = userPreferencesDataStore.getInterestedGenres()
        _savedAreas.value = userPreferencesDataStore.getInterestedAreas()
    }

    suspend fun saveSelections(genreCodes: Set<String>, areaCodes: Set<String>) {
        userPreferencesDataStore.setInterestedGenres(genreCodes)
        userPreferencesDataStore.setInterestedAreas(areaCodes)
    }
}
