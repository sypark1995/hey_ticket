package com.sypark.openTicket.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sypark.data.db.entity.OpenTicket
import com.sypark.data.repository.MainRepository
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    private val _backgroundPos = MutableLiveData<Int>()

    val backgroundPos: LiveData<Int>
        get() = _backgroundPos

    var isLoading: Boolean = false
    var toastMessage: String = " "

    fun setBackgroundPosition(position: Int) {
        _backgroundPos.postValue(position)
    }

    private var _interParkList = MutableLiveData<List<OpenTicket>>()

    val interParkList: LiveData<List<OpenTicket>>
        get() = _interParkList

    private var _melonList = MutableLiveData<List<OpenTicket>>()

    var melonList: LiveData<List<OpenTicket>>
        get() = _melonList

    init {
        melonList = _melonList
    }

    suspend fun getHitsInterParkData() {
        mainRepository.getInterParkOpenTicket(
            genre = "all",
            order = "viewed",
            pageIndex = "1",
            size = null,
            onStart = { isLoading = true },
            onComplete = { isLoading = false },
            onError = {}
        )!!.flowOn(Dispatchers.IO)
            .catch { e ->
                Log.e("catch", e.toString())
            }.collect {
                Log.e("!!!!!!!!", it.toString())
//                _interParkList.value?.addAll(it)

            }
    }

    suspend fun getHitsMelonData() {
        mainRepository.getMelonOpenTicket(
            genre = "all",
            order = "viewed",
            pageIndex = "1",
            size = null,
            onStart = { isLoading = true },
            onComplete = { isLoading = false },
            onError = {}
        )!!.flowOn(Dispatchers.IO)
            .catch { e ->
                Log.e("catch", e.toString())
            }.collect {
                Log.e("!!!!!!", it.toString())
                _melonList.postValue(it)
            }
    }

}