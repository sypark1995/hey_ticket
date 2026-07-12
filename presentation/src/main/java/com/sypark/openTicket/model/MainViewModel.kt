package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.Content
import com.sypark.domain.usecase.GetPerformanceNewUseCase
import com.sypark.openTicket.base.BaseViewModel
import com.sypark.openTicket.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPerformanceNewUseCase: GetPerformanceNewUseCase,
) : BaseViewModel() {

    private var _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorEvent = SingleLiveEvent<Unit>()
    val errorEvent: LiveData<Unit> = _errorEvent

    var toastMessage: String = ""

    private var _newTicketList = MutableLiveData<List<Content>>()
    var newTicketList: LiveData<List<Content>> = _newTicketList

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
}
