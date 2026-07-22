package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.Content
import com.sypark.domain.usecase.GetClosingSoonUseCase
import com.sypark.domain.usecase.GetMatchingNewUseCase
import com.sypark.domain.usecase.GetPerformanceDetailUseCase
import com.sypark.domain.usecase.GetPerformanceNewUseCase
import com.sypark.openTicket.base.BaseViewModel
import com.sypark.openTicket.base.SingleLiveEvent
import com.sypark.openTicket.util.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPerformanceNewUseCase: GetPerformanceNewUseCase,
    private val getClosingSoonUseCase: GetClosingSoonUseCase,
    private val getMatchingNewUseCase: GetMatchingNewUseCase,
    private val getPerformanceDetailUseCase: GetPerformanceDetailUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) : BaseViewModel() {

    private var _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorEvent = SingleLiveEvent<Unit>()
    val errorEvent: LiveData<Unit> = _errorEvent

    var toastMessage: String = ""

    private var _newTicketList = MutableLiveData<List<Content>>()
    var newTicketList: LiveData<List<Content>> = _newTicketList

    private var _musicalTicketList = MutableLiveData<List<Content>>()
    val musicalTicketList: LiveData<List<Content>> = _musicalTicketList

    private var _theaterTicketList = MutableLiveData<List<Content>>()
    val theaterTicketList: LiveData<List<Content>> = _theaterTicketList

    private var _closingSoonList = MutableLiveData<List<Content>>()
    val closingSoonList: LiveData<List<Content>> = _closingSoonList

    private var _recommendedList = MutableLiveData<List<Content>>()
    val recommendedList: LiveData<List<Content>> = _recommendedList

    private var _favoritesList = MutableLiveData<List<Content>>()
    val favoritesList: LiveData<List<Content>> = _favoritesList

    private var _mainSelector = MutableLiveData(false)
    val mainSelector: LiveData<Boolean> = _mainSelector

    suspend fun getNewTicketData(
        genre: String?,
        sortType: String? = "",
        sortOrder: String? = "",
        page: Int? = 1,
        pageSize: Int? = 10
    ) {
        getPerformanceNewUseCase(genre, page ?: 1, pageSize ?: 10).collect { result ->
            when (result) {
                is ApiResult.Success -> if (result.value.isNotEmpty()) {
                    _newTicketList.value = result.value
                }
                is ApiResult.Error -> {
                    _isLoading.value = false
                    _errorEvent.call()
                }
                is ApiResult.Loading -> Unit
            }
        }
    }

    suspend fun getMusicalTicketData() {
        getPerformanceNewUseCase("MUSICAL", 1, 10).collect { result ->
            if (result is ApiResult.Success && result.value.isNotEmpty()) {
                _musicalTicketList.value = result.value
            }
        }
    }

    suspend fun getTheaterTicketData() {
        getPerformanceNewUseCase("THEATER", 1, 10).collect { result ->
            if (result is ApiResult.Success && result.value.isNotEmpty()) {
                _theaterTicketList.value = result.value
            }
        }
    }

    suspend fun getClosingSoonData() {
        getClosingSoonUseCase(10).collect { result ->
            if (result is ApiResult.Success && result.value.isNotEmpty()) {
                _closingSoonList.value = result.value
            }
        }
    }

    suspend fun getRecommendedData() {
        val genres = userPreferencesDataStore.getInterestedGenres()
        val areas = userPreferencesDataStore.getInterestedAreas()

        if (genres.isEmpty() && areas.isEmpty()) return

        val genreBuckets = if (genres.isEmpty()) listOf<String?>(null) else genres.toList()
        val areaBuckets = if (areas.isEmpty()) listOf<String?>(null) else areas.toList()

        val merged = mutableListOf<Content>()
        for (genre in genreBuckets) {
            for (area in areaBuckets) {
                getMatchingNewUseCase(genre, area, rows = 10).collect { result ->
                    if (result is ApiResult.Success) {
                        merged += result.value
                    }
                }
            }
        }

        val deduped = merged.distinctBy { it.id }
        if (deduped.isNotEmpty()) {
            _recommendedList.value = deduped
        }
    }

    suspend fun getFavoritesData() {
        val ids = userPreferencesDataStore.getFavoriteIds()
        if (ids.isEmpty()) return

        val results = coroutineScope {
            ids.map { id -> async { fetchDetailOrNull(id) } }.map { it.await() }
        }
        val favorites = results.filterNotNull()
        if (favorites.isNotEmpty()) {
            _favoritesList.value = favorites
        }
    }

    private suspend fun fetchDetailOrNull(performanceId: String): Content? {
        var result: Content? = null
        getPerformanceDetailUseCase(performanceId).collect {
            if (it is ApiResult.Success) {
                result = it.value
            }
        }
        return result
    }
}
