package com.sypark.openTicket.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sypark.data.db.entity.ApiResult
import com.sypark.data.db.entity.LoginVerification
import com.sypark.data.repository.LoginRepository
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFirstViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    private var mutableEmailAddress = MutableLiveData<String>()
    var emailAddress: LiveData<String>
        get() = mutableEmailAddress

    init {
        emailAddress = mutableEmailAddress
    }

    fun setEmailAddress(email: String) {
        mutableEmailAddress.postValue(email)
    }

    fun getLoginValidation(success: (Boolean) -> Unit) {
        viewModelScope.launch {
            loginRepository.getLoginValidation(LoginVerification(mutableEmailAddress.value.toString()))
                .flowOn(Dispatchers.IO).collectLatest { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            success(
                                Gson().fromJson(
                                    result.value.data, object : TypeToken<Boolean>() {}.type
                                )
                            )
                        }
                        is ApiResult.Error -> {

                        }
                        is ApiResult.Loading -> {
                            Log.e("!!!!!","")
                        }
                    }
                }
        }
    }

}