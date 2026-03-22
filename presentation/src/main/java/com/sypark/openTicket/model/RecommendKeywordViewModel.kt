package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendKeywordViewModel @Inject constructor(
) : BaseViewModel() {
    private var mutableKeywordText = MutableLiveData<String>()
    var keywordText: LiveData<String> = mutableKeywordText

    var isMutablePushAgree = MutableLiveData(false)
    var isPushAgree: LiveData<Boolean> = isMutablePushAgree

    var isMutablePersonalAgree = MutableLiveData(false)
    var isPersonalAgree: LiveData<Boolean> = isMutablePersonalAgree

    fun setKeywordText(text: String) {
        mutableKeywordText.postValue(text)
    }

    fun isPushAgree() {
        isMutablePushAgree.value = isMutablePushAgree.value == false
    }

    fun isPersonalAgree() {
        isMutablePersonalAgree.value = isMutablePersonalAgree.value == false
    }
}