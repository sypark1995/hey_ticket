package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginFindViewModel @Inject constructor(
) : BaseViewModel() {
    private var mutableEmail = MutableLiveData<String>()
    var email: LiveData<String>
        get() = mutableEmail

    init {
        email = mutableEmail
    }

    fun setEmail(email: String) {
        mutableEmail.postValue(email)
    }

}