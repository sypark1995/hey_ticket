package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.domain.repository.MainRepository
import com.sypark.openTicket.base.BaseViewModel
import dagger.Binds
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    private val _backgroundPos = MutableLiveData<Int>()

    val backgroundPos: LiveData<Int>
        get() = _backgroundPos

    @Binds
    fun setBackgroundPosition(position: Int) {
        _backgroundPos.postValue(position)
    }

//    @Binds
    suspend fun getData() {
        mainRepository.getMelonOpenTicket(

        )
    }
}