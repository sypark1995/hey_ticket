package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sypark.data.db.entity.CategoryDetailArea
import com.sypark.data.db.entity.Content
import com.sypark.data.paging.PagingRepository
import com.sypark.data.util.Util
import com.sypark.openTicket.base.BaseViewModel
import com.sypark.openTicket.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val repository: PagingRepository
) : BaseViewModel() {

    private val _isFilterShow = MutableLiveData(false)
    val isFilterShow: LiveData<Boolean>
        get() = _isFilterShow

    fun setFilterShow(isFilterShow: Boolean) {
        _isFilterShow.value = isFilterShow
    }

    private val _isSortShow = MutableLiveData(false)
    val isSortShow: LiveData<Boolean>
        get() = _isSortShow

    fun setSortShow(isSortShow: Boolean) {
        _isSortShow.value = isSortShow
    }

    private val _filterType = MutableLiveData(FilterType.AREA)
    val filterType: LiveData<FilterType>
        get() = _filterType

    fun setFilterType(type: FilterType) {
        _filterType.value = type
    }


    private val _chipType = MutableLiveData(FilterType.AREA)

    val chipType: LiveData<FilterType>
        get() = _chipType


    enum class FilterType {
        AREA, DAY, STATUS, PRICE
    }

    private var _isOpen = MutableLiveData(false)
    val isOpen: LiveData<Boolean> = _isOpen

    fun setIsOpen(isOpenCheck: Boolean) = _isOpen.postValue(isOpenCheck)

    private var _sortType = SingleLiveEvent<String>()
    val sortType: LiveData<String> = _sortType

    private var _isDetailSortLayoutVisibility = MutableLiveData(false)
    val isDetailSortVisibility: LiveData<Boolean> = _isDetailSortLayoutVisibility

    private var _isFilterLayoutVisibility = MutableLiveData(false)
    val isFilterLayoutVisibility: LiveData<Boolean> = _isFilterLayoutVisibility

    private var _genre = MutableLiveData<String>()
    private val genre: LiveData<String> = _genre

    fun setDetailSortLayoutVisibility(isVisibility: Boolean) {
        _isDetailSortLayoutVisibility.value = isVisibility
    }

    fun setFilterLayoutVisibility(isVisibility: Boolean) {
        _isFilterLayoutVisibility.value = isVisibility
    }

    fun setSortType(type: String) {
        _sortType.value = type
//        _sortType.postValue(type)
    }

    fun setGenre(genre: String): Flow<PagingData<Content>> {
        _genre.value = genre

        return repository.getPagingData(
            boxOfficeGenre = _genre.value.toString(),
            timePeriod = Util.ButtonType.DAY
        ).cachedIn(viewModelScope)
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

    private var _selectedDay = MutableLiveData<String?>()
    val selectedDay: LiveData<String?>
        get() = _selectedDay


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

    fun setSelectedDay(day: String) = _selectedDay.postValue(day)
    fun clearSelectedDay() {
        _selectedDay.value = null
    }

}

