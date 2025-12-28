package com.sypark.openTicket.model

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.openTicket.base.BaseViewModel
import com.sypark.openTicket.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(

) : BaseViewModel() {
    private var _isOpen = MutableLiveData(false)
    val isOpen: LiveData<Boolean> = _isOpen

    fun setIsOpen(isOpenCheck: Boolean) = _isOpen.postValue(isOpenCheck)

    private var _sortType = SingleLiveEvent<String>()
    val sortType: LiveData<String> = _sortType

    fun setSortType(type: String) {
        _sortType.value = type
//        _sortType.postValue(type)
    }

    var isPlaned = MutableLiveData(false)
    val isDuring = MutableLiveData(false)
    val isFinished = MutableLiveData(false)

    fun isPlanedChecked() {
        isPlaned.value = isPlaned.value == false
    }

    fun isDuringChecked() {
        isDuring.value = isDuring.value == false
    }

    fun isFinishedChecked() {
        isFinished.value = isFinished.value == false
    }
}