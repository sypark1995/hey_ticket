package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginSecondViewModel @Inject constructor(
) : BaseViewModel() {
    private var mutableEmailPw = MutableLiveData<String>()
    var emailPw: LiveData<String> = mutableEmailPw

    fun setEmailPw(pw: String) {
        mutableEmailPw.postValue(pw)
    }
}