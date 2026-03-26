package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.data.db.entity.Content
import com.sypark.data.repository.PlaceDetailRepository
import com.sypark.data.repository.TicketDetailRepository
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class TicketDetailViewModel @Inject constructor(
    private val ticketDetailRepository: TicketDetailRepository,
    private val placeDetailRepository: PlaceDetailRepository
) : BaseViewModel() {

    private val _ticketDetail = MutableLiveData<Content>()
    val ticketDetail: LiveData<Content> = _ticketDetail

    suspend fun getTicketDetailData(mt20id: String) {
        ticketDetailRepository.getTicketDetail(mt20id).flowOn(Dispatchers.IO).catch {

        }.collect {
            if (it.data.contents.isNotEmpty()) {
                _ticketDetail.value = it.data.contents
            }
        }
    }
//    suspend fun getPlaceDetailData(mt10id: String) {
//        _placeDetail.value = placeDetailRepository.getPlaceDetail(mt10id)
//    }
}