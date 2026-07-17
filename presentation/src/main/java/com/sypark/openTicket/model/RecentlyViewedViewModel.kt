package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.Content
import com.sypark.domain.usecase.GetPerformanceDetailUseCase
import com.sypark.openTicket.base.BaseViewModel
import com.sypark.openTicket.util.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecentlyViewedViewModel @Inject constructor(
    private val getPerformanceDetailUseCase: GetPerformanceDetailUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) : BaseViewModel() {

    private val _recentlyViewed = MutableLiveData<List<Content>>()
    val recentlyViewed: LiveData<List<Content>> = _recentlyViewed

    // 병렬(async/awaitAll)로 조회하면 완료 순서가 뒤섞여 저장된 "최신순" 순서가 깨지므로 순차 조회한다.
    suspend fun loadRecentlyViewed() {
        val ids = userPreferencesDataStore.getRecentlyViewedIds()
        val results = ids.mapNotNull { id -> fetchOrNull(id) }
        _recentlyViewed.value = results
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
}
