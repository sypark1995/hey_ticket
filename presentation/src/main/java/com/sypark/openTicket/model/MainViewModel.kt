package com.sypark.openTicket.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.Content
import com.sypark.domain.usecase.GetPerformanceNewUseCase
import com.sypark.domain.usecase.GetPerformanceRankingUseCase
import com.sypark.openTicket.base.BaseViewModel
import com.sypark.openTicket.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPerformanceRankingUseCase: GetPerformanceRankingUseCase,
    private val getPerformanceNewUseCase: GetPerformanceNewUseCase,
) : BaseViewModel() {

    private var _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorEvent = SingleLiveEvent<Unit>()
    val errorEvent: LiveData<Unit> = _errorEvent

    var toastMessage: String = ""

    private var _rankingList = MutableLiveData<List<Content>>()
    var rankingList: LiveData<List<Content>> = _rankingList

    private var _newTicketList = MutableLiveData<List<Content>>()
    var newTicketList: LiveData<List<Content>> = _newTicketList

    private var _isMutableShimmerLoading = MutableLiveData(false)
    val isShimmerLoading: LiveData<Boolean> = _isMutableShimmerLoading

    private var _mainSelector = MutableLiveData(false)
    val mainSelector: LiveData<Boolean> = _mainSelector

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getRankingData(boxOfficeGenre: String?, page: Int? = 1, pageSize: Int? = 10) {
        getPerformanceRankingUseCase("day", boxOfficeGenre, null).collect { result ->
            when (result) {
                is ApiResult.Success -> if (result.value.isNotEmpty()) {
                    _rankingList.value = result.value
                    _isMutableShimmerLoading.value = true
                }
                is ApiResult.Error -> {
                    _isMutableShimmerLoading.value = false
                    _errorEvent.call()
                }
                is ApiResult.Loading -> Unit
            }
        }
    }

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
