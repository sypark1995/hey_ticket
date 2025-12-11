package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : BaseViewModel() {
    private var _radioState = MutableLiveData<String>()
    var radioState: LiveData<String>
        get() = _radioState

    init {
        radioState = _radioState
    }

    fun getRadioState(data: String) {
        _radioState.value = data
    }
}