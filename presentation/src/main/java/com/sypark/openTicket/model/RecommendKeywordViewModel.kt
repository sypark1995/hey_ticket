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

    fun setKeywordText(text: String) {
        mutableKeywordText.postValue(text)
    }
}