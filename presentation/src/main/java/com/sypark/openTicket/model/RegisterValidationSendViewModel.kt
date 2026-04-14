package com.sypark.openTicket.model

import androidx.lifecycle.viewModelScope
import com.sypark.data.db.entity.ApiResult
import com.sypark.data.db.entity.BaseResponse
import com.sypark.data.db.entity.request.RegisterValidationSend
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
class RegisterValidationSendViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseViewModel() {
    private val _validationResponse: MutableSharedFlow<ApiResult<BaseResponse>> =
        MutableSharedFlow()

    val validationResponse: SharedFlow<ApiResult<BaseResponse>> = _validationResponse.asSharedFlow()


    fun getRegisterValidationSend2(registerValidationSend: RegisterValidationSend) {
        viewModelScope.launch {
            //todo_sypark 방식이 맞는지 모르겠음 collect viewModel + activity에서 2번 처리 flow를 까기위해 이런식으로???
            _validationResponse.emit(ApiResult.Loading)

            loginRepository.getRegisterValidationSend(
                registerValidationSend
            ).collectLatest {
                _validationResponse.emit(it)
            }
        }
    }
}


