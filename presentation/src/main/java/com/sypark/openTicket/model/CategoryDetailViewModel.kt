package com.sypark.openTicket.model

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.data.db.entity.CategoryDetailArea
import com.sypark.openTicket.base.BaseViewModel
import com.sypark.openTicket.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(

) : BaseViewModel() {
    private var _isOpen = MutableLiveData(false)
    val isOpen: LiveData<Boolean> = _isOpen

    fun setIsOpen(isOpenCheck: Boolean) = _isOpen.postValue(isOpenCheck)

    private var _sortType = SingleLiveEvent<String>()
    val sortType: LiveData<String> = _sortType

    fun setSortType(type: String) {
        _sortType.value = type
//        _sortType.postValue(type)
    }

    private var _filterArea = MutableLiveData(true)
    private var _filterDay = MutableLiveData(false)
    private var _filterStatus = MutableLiveData(false)
    private var _filterPrice = MutableLiveData(false)

    var isPlaned = MutableLiveData(false)
    val isDuring = MutableLiveData(false)
    val isFinished = MutableLiveData(false)

    private var _statusList = MutableLiveData<ArrayList<String>>()
    val statusList: LiveData<ArrayList<String>> = _statusList

    val filterArea: LiveData<Boolean>
        get() = _filterArea

    val filterDay: LiveData<Boolean>
        get() = _filterDay

    val filterStatus: LiveData<Boolean>
        get() = _filterStatus

    val filterPrice: LiveData<Boolean>
        get() = _filterPrice

    private var _filterAreaData = MutableLiveData<ArrayList<CategoryDetailArea>>()
    val filterAreaData: LiveData<ArrayList<CategoryDetailArea>>
        get() = _filterAreaData

    private var _filterPriceData = MutableLiveData<String?>()
    val filterPriceData: LiveData<String?>
        get() = _filterPriceData


    fun isPlanedChecked() {
        isPlaned.value = isPlaned.value == false
    }

    fun isDuringChecked() {
        isDuring.value = isDuring.value == false
    }

    fun isFinishedChecked() {
        isFinished.value = isFinished.value == false
    }

    fun isShowFilterArea(isShow: Boolean) = _filterArea.postValue(isShow)
    fun isShowFilterDay(isShow: Boolean) = _filterDay.postValue(isShow)
    fun isShowFilterStatus(isShow: Boolean) = _filterStatus.postValue(isShow)
    fun isShowFilterPrice(isShow: Boolean) = _filterPrice.postValue(isShow)

    fun setFilterAreaList(list: ArrayList<CategoryDetailArea>) = _filterAreaData.postValue(list)
    fun setFilterPrice(price: String) = _filterPriceData.postValue(price)
    fun clearFilterPrice() {
        _filterPriceData.value = null
    }

    fun setFilterStatus(status: ArrayList<String>) = _statusList.postValue(status)
}