package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.Content
import com.sypark.domain.usecase.GetClosingSoonUseCase
import com.sypark.domain.usecase.GetPerformanceNewUseCase
import com.sypark.openTicket.base.BaseViewModel
import com.sypark.openTicket.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPerformanceNewUseCase: GetPerformanceNewUseCase,
    private val getClosingSoonUseCase: GetClosingSoonUseCase,
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
}
