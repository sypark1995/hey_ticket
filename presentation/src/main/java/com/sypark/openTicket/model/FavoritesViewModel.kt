package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.Content
import com.sypark.domain.usecase.GetPerformanceDetailUseCase
import com.sypark.openTicket.base.BaseViewModel
import com.sypark.openTicket.util.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getPerformanceDetailUseCase: GetPerformanceDetailUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) : BaseViewModel() {

    private val _favorites = MutableLiveData<List<Content>>()
    val favorites: LiveData<List<Content>> = _favorites

    suspend fun loadFavorites() {
        val ids = userPreferencesDataStore.getFavoriteIds()
        val results = coroutineScope {
            ids.map { id -> async { fetchOrNull(id) } }.map { it.await() }
        }
        _favorites.value = results.filterNotNull()
    }

    private suspend fun fetchOrNull(performanceId: String): Content? {
        var result: Content? = null
        getPerformanceDetailUseCase(performanceId).collect {
            if (it is ApiResult.Success) {
                result = it.value
            }
        }
        return result
    }

    suspend fun removeFavorite(performanceId: String) {
        userPreferencesDataStore.toggleFavorite(performanceId)
        _favorites.value = _favorites.value.orEmpty().filterNot { it.id == performanceId }
    }
}
