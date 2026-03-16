package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterPasswordViewModel @Inject constructor(
) : BaseViewModel() {

    private var mutableEmailCode = MutableLiveData<String>()
    val emailCode: LiveData<String> = mutableEmailCode

    fun setEmailCode(code: String) {
        mutableEmailCode.postValue(code)
    }

}