package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.data.db.entity.Areas
import com.sypark.data.db.entity.Genre
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor() : BaseViewModel() {

    private var _email = MutableLiveData<String?>()
    val email: LiveData<String?> = _email

    private var _pw = MutableLiveData<String?>()
    val pw: LiveData<String?> = _pw

    private var _verificationCode = MutableLiveData<String?>()
    val verificationCode: LiveData<String?> = _verificationCode

    private var _genres = MutableLiveData<ArrayList<Genre>?>()
    val genres: LiveData<ArrayList<Genre>?> = _genres

    private var _areas = MutableLiveData<ArrayList<Areas>?>()
    val areas: LiveData<ArrayList<Areas>?> = _areas

    private var _keywords = MutableLiveData<ArrayList<String>>()
    val keywords: LiveData<ArrayList<String>> = _keywords

    private var _keywordPush = MutableLiveData<Boolean>()
    val keywordPush: LiveData<Boolean> = _keywordPush


    private var _isPushAgree = MutableLiveData(false)
    var isPushAgree: LiveData<Boolean> = _isPushAgree

    private var _isProvisionAgree = MutableLiveData(false)
    var isProvisionAgree: LiveData<Boolean> = _isProvisionAgree

    private var _isRegisterAgree = MutableLiveData(false)
    var isRegisterAgree: LiveData<Boolean> = _isRegisterAgree

    fun setEmail(email: String? = null) {
        _email.value = email
    }

    fun setVerificationCode(verificationCode: String? = null) {
        _verificationCode.value = verificationCode
    }

    fun setPw(pw: String? = null) {
        _pw.value = pw
    }

    fun setGenres(genre: ArrayList<Genre>? = null) {
        _genres.value = genre
    }

    fun setAreas(areas: ArrayList<Areas>? = null) {
        _areas.value = areas
    }

    fun setKeyWords(keywords: ArrayList<String>) {
        _keywords.value = keywords
    }

    fun isPushAgree() {
        _isPushAgree.value = _isPushAgree.value == false
    }

    fun isProvisionAgree() {
        _isProvisionAgree.value = _isProvisionAgree.value == false
    }

    fun reSetPushAgree() {
        _isPushAgree.value = false
    }

    fun reSetProvisionAgree() {
        _isProvisionAgree.value = false
    }

    fun isRegisterFinish() {
        //todo_sypark 로직 수정 예정
        if (_keywords.value.isNullOrEmpty()) {
            _isRegisterAgree.value = _isProvisionAgree.value == true
        } else {
            _isRegisterAgree.value =
                _isPushAgree.value == true && _isProvisionAgree.value == true
        }
    }

    fun reSetRegisterFinish() {
        _isRegisterAgree.value = false
    }

}