package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.data.db.entity.TicketDetail
import com.sypark.data.repository.TicketDetailRepository
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TicketDetailViewModel @Inject constructor(
    private val ticketDetailRepository: TicketDetailRepository
) : BaseViewModel() {

    private val _ticketDetail = MutableLiveData<TicketDetail>()
    val ticketDetail : LiveData<TicketDetail> = _ticketDetail

    suspend fun getTicketDetailData(mt20id: String) {
        if (ticketDetailRepository.getTicketDetail(mt20id) == null) {

        } else {
            _ticketDetail.value = ticketDetailRepository.getTicketDetail(mt20id)
        }
    }
}