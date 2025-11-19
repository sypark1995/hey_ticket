package com.sypark.openTicket.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sypark.data.repository.MainRepository
import com.sypark.openTicket.base.BaseViewModel
import dagger.Binds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
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
            )!!.flowOn(Dispatchers.IO)
                .catch { e ->
                    Log.e("catch", e.toString())
                }.collect {
                    Log.e("!!!!", it.toString())
                }
        }
    }
}