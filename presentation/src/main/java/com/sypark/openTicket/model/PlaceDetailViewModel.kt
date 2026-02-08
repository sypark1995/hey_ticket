package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.data.db.entity.PlaceDetail
import com.sypark.data.db.entity.TicketDetail
import com.sypark.data.repository.PlaceDetailRepository
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaceDetailViewModel @Inject constructor(
    private val placeDetailRepository: PlaceDetailRepository
) : BaseViewModel() {
    private val _placeDetail = MutableLiveData<PlaceDetail>()
    val placeDetail: LiveData<PlaceDetail> = _placeDetail

    suspend fun getPlaceDetailData(mt10id: String) {
        if (placeDetailRepository.getPlaceDetail(mt10id) == null) {

        } else {
            _placeDetail.value = placeDetailRepository.getPlaceDetail(mt10id)
        }
    }
}