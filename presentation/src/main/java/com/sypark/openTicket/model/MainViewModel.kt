package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sypark.domain.repository.MainRepository
import com.sypark.openTicket.base.BaseViewModel
import dagger.Binds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    private val _backgroundPos = MutableLiveData<Int>()

    val backgroundPos: LiveData<Int>
        get() = _backgroundPos

    var isLoading: Boolean = false
    var toastMessage: String = " "

    @Binds
    fun setBackgroundPosition(position: Int) {
        _backgroundPos.postValue(position)
    }


    fun getData() {
        viewModelScope.launch {
            mainRepository.getInterParkOpenTicket(
                genre = "all",
                order = "viewed",
                pageIndex = "1",
                size = null,
                onStart = {isLoading = true},
                onComplete = {isLoading = false},
                onError = {}
            )
        }
    }
}