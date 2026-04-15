package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sypark.data.db.entity.ApiResult
import com.sypark.data.db.entity.BaseResponse
import com.sypark.data.db.entity.request.RegisterValidationVerify
import com.sypark.data.repository.LoginRepository
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterFirstViewModel @Inject constructor(
    private val repository: LoginRepository
) : BaseViewModel() {
    private var mutableEmailCode = MutableLiveData<String>()
    val emailCode: LiveData<String> = mutableEmailCode

    private var isMutableEmailCodeError = MutableLiveData<Boolean>()
    val isEmailCodeError: LiveData<Boolean> = isMutableEmailCodeError

    private var isMutableTimeOut = MutableLiveData<Boolean>()
    val isTimeOut: LiveData<Boolean> = isMutableTimeOut

    private val _response: MutableSharedFlow<ApiResult<BaseResponse>> =
        MutableSharedFlow()

    val response: SharedFlow<ApiResult<BaseResponse>> = _response.asSharedFlow()

    fun setEmailCode(code: String) {
        mutableEmailCode.value = code
    }

    fun setEmailErrorCode(boolean: Boolean) {
        isMutableEmailCodeError.value = boolean
    }

    fun isTimeOut(isTimeOut: Boolean) {
        isMutableTimeOut.value = isTimeOut
    }

    fun getRegisterValidationVerify(registerValidationVerify: RegisterValidationVerify) {
        viewModelScope.launch {
            _response.emit(ApiResult.Loading)

            repository.getRegisterVerify(registerValidationVerify).collectLatest {
                _response.emit(it)
            }
        }
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