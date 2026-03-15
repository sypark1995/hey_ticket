package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterFirstViewModel @Inject constructor(
) : BaseViewModel() {
    private var mutableEmailCode = MutableLiveData<String>()
    val emailCode: LiveData<String> = mutableEmailCode

    private var isMutableEmailCodeError = MutableLiveData<Boolean>()
    val isEmailCodeError: LiveData<Boolean> = isMutableEmailCodeError

    fun setEmailCode(code: String) {
        mutableEmailCode.postValue(code)
    }

    fun resetEmailCode() {
        mutableEmailCode.postValue("")
    }

    fun setEmailErrorCode(boolean: Boolean) {
        isMutableEmailCodeError.postValue(boolean)
    }

    fun resetEmailErrorCode() {
        isMutableEmailCodeError.postValue(false)
    }

//    private val _countDown = MutableLiveData(10)
//    val countDown: LiveData<Int>
//        get() = _countDown
//
//    fun resetCountDown() {
//        _countDown.postValue(10)
//    }
//
//    fun countDownTick() {
//        val count = _countDown.value ?: 10
//        val nextCount = max(count - 1, 0)
//        _countDown.postValue(nextCount)
//    }

}