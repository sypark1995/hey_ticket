package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginFirstViewModel @Inject constructor(
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
}