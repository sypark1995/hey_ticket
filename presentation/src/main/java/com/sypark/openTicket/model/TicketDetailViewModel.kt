package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.TicketDetail
import com.sypark.domain.usecase.GetTicketDetailUseCase
import com.sypark.openTicket.base.BaseViewModel
import com.sypark.openTicket.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TicketDetailViewModel @Inject constructor(
    private val getTicketDetailUseCase: GetTicketDetailUseCase,
) : BaseViewModel() {

    private val _ticketDetail = MutableLiveData<TicketDetail>()
    val ticketDetail: LiveData<TicketDetail> = _ticketDetail

    private var _isLoading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _isLoading

    private val _errorEvent = SingleLiveEvent<Unit>()
    val errorEvent: LiveData<Unit> = _errorEvent

    private var _scrollYPosition = MutableLiveData<Int>()
    val scrollYPosition: LiveData<Int> = _scrollYPosition

    suspend fun getTicketDetailData(id: String) {
        getTicketDetailUseCase(id).collect { result ->
            when (result) {
                is ApiResult.Success -> {
                    _isLoading.value = true
                    _ticketDetail.value = result.value
                }
                is ApiResult.Error -> {
                    _isLoading.value = false
                    _errorEvent.call()
                }
                is ApiResult.Loading -> Unit
            }
        }
    }

    fun setScrollYPosition(position: Int) {
        _scrollYPosition.value = position
    }
}
