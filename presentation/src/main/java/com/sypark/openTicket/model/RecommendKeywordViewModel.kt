package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sypark.data.db.entity.ApiResult
import com.sypark.data.db.entity.BaseResponse
import com.sypark.data.db.entity.request.Signup
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
class RecommendKeywordViewModel @Inject constructor(
    val repository: LoginRepository
) : BaseViewModel() {
    private var mutableKeywordText = MutableLiveData<String?>()
    var keywordText: LiveData<String?> = mutableKeywordText

    private val _response: MutableSharedFlow<ApiResult<BaseResponse>> =
        MutableSharedFlow()

    val response: SharedFlow<ApiResult<BaseResponse>> = _response.asSharedFlow()

    fun setKeywordText(text: String? = null) {
        mutableKeywordText.postValue(text)
    }

    fun getSignUp(signup: Signup) {
        viewModelScope.launch {
            _response.emit(ApiResult.Loading)

            repository.getSignUp(signup).collectLatest {
                _response.emit(it)
            }
        }
    }
}