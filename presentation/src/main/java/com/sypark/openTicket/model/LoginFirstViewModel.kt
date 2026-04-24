package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sypark.data.db.entity.ApiResult
import com.sypark.data.db.entity.BaseResponse
import com.sypark.data.db.entity.request.LoginVerification
import com.sypark.data.repository.LoginRepository
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFirstViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    private var mutableEmailAddress = MutableLiveData<String>()
    var emailAddress: LiveData<String>
        get() = mutableEmailAddress

    private val _response: MutableSharedFlow<ApiResult<BaseResponse>> =
        MutableSharedFlow()
    val response: SharedFlow<ApiResult<BaseResponse>> = _response.asSharedFlow()

    init {
        emailAddress = mutableEmailAddress
    }

    fun setEmailAddress(email: String) {
        mutableEmailAddress.postValue(email)
    }

    fun getLoginValidation() {
        viewModelScope.launch {
            _response.emit(ApiResult.Loading)

            loginRepository.getLoginValidation(LoginVerification(mutableEmailAddress.value.toString()))
                .flowOn(Dispatchers.IO).collectLatest { result ->
                    _response.emit(result)
                }
        }
    }

}