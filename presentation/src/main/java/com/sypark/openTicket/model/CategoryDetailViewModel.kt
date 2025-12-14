package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(

) : BaseViewModel() {
    private var _isOpen = MutableLiveData(false)
    val isOpen: LiveData<Boolean>
        get() = _isOpen

    fun setIsOpen(isOpenCheck: Boolean) = _isOpen.postValue(isOpenCheck)
}