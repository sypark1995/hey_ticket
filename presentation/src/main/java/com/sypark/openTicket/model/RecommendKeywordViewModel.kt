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

    var isMutableRegisterFinish = MutableLiveData(false)
    var isRegisterFinish: LiveData<Boolean> = isMutableRegisterFinish

    fun setKeywordText(text: String) {
        mutableKeywordText.postValue(text)
    }

    fun isPushAgree() {
        isMutablePushAgree.value = isMutablePushAgree.value == false
    }

    fun isPersonalAgree() {
        isMutablePersonalAgree.value = isMutablePersonalAgree.value == false
    }

    fun isRegisterFinish() {
        //todo_sypark 로직 수정 예정
        isMutableRegisterFinish.value =
            isMutablePushAgree.value == true && isMutablePersonalAgree.value == true
    }
}