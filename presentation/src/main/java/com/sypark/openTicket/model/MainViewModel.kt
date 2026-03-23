package com.sypark.openTicket.model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.data.db.entity.Content
import com.sypark.data.db.entity.OpenTicket
import com.sypark.data.repository.MainRepository
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    private var _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    var toastMessage: String = ""

    private var _interParkList = MutableLiveData<List<OpenTicket>>()

    val interParkList: LiveData<List<OpenTicket>>
        get() = _interParkList

    private var _melonList = MutableLiveData<List<OpenTicket>>()

    var melonList: LiveData<List<OpenTicket>>
        get() = _melonList

    private var _rankingList = MutableLiveData<List<Content>>()

    var rankingList: LiveData<List<Content>> = _rankingList

    init {
        melonList = _melonList
    }

    private var _mainSelector = MutableLiveData(false)
    val mainSelector: LiveData<Boolean> = _mainSelector

//    suspend fun getHitsInterParkData() {
//        mainRepository.getInterParkOpenTicket(
//            genre = "all",
//            order = "viewed",
//            pageIndex = "1",
//            size = null,
//            onStart = { _isLoading.value = true },
//            onComplete = { _isLoading.value = false },
//            onError = {}
//        )!!.flowOn(Dispatchers.IO)
//            .catch { e ->
//                Log.e("catch", e.toString())
//            }.collect {
//                Log.e("!!!!!!!!", it.toString())
////                _interParkList.value?.addAll(it)
//
//            }
//    }
//
//    suspend fun getHitsMelonData() {
//        mainRepository.getMelonOpenTicket(
//            genre = "all",
//            order = "viewed",
//            pageIndex = "1",
//            size = null,
//            onStart = { _isLoading.postValue(true) },
//            onComplete = { _isLoading.postValue(false) },
//            onError = {}
//        )!!.flowOn(Dispatchers.IO)
//            .catch { e ->
//                Log.e("catch", e.toString())
//            }.collect {
//                Log.e("_isLoading",_isLoading.value.toString())
//                Log.e("!!!!!!", it.toString())
//                _melonList.value = it
//            }
//    }


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getRankingData(genre: String?, page: Int? = 0, pageSize: Int? = 10) {
        repository.getRankingTicket(
            timePeriod = "",
            LocalDate.now().toString(),
            genre = genre,
            area = "",
            page = page,
            pageSize = pageSize,
        ).flowOn(Dispatchers.IO)
            .catch {
                Log.e("!!!!", it.toString())
            }.collect {
                if (it.data.contents.isNotEmpty()) {
                    _rankingList.value = it.data.contents
                }
            }
    }

}