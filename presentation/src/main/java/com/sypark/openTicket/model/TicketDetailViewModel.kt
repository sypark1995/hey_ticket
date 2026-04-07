package com.sypark.openTicket.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sypark.data.db.entity.TicketDetail
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
) : BaseViewModel() {

    private val _ticketDetail = MutableLiveData<TicketDetail>()
    val ticketDetail: LiveData<TicketDetail> = _ticketDetail

    private var _isLoading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _isLoading

    private var _scrollYPosition = MutableLiveData<Int>()
    val scrollYPosition: LiveData<Int> = _scrollYPosition

    suspend fun getTicketDetailData(id: String) {
        ticketDetailRepository.getTicketDetail(id).flowOn(Dispatchers.IO).catch {
            Log.e("getTicketDetailData", it.toString())
            _isLoading.value = false
        }.collect {
            val data = Gson().fromJson<TicketDetail>(
                it.data,
                object : TypeToken<TicketDetail>() {}.type
            )
            _isLoading.value = true
            _ticketDetail.value = data
        }
    }

    fun setScrollYPosition(position: Int) {
        _scrollYPosition.value = position
    }
}