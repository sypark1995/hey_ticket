package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sypark.data.db.entity.Areas
import com.sypark.data.db.entity.Content
import com.sypark.data.paging.PagingRepository
import com.sypark.data.util.Util
import com.sypark.openTicket.Common
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseViewModel
import com.sypark.openTicket.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val repository: PagingRepository
) : BaseViewModel() {

    enum class FilterBtnType {
        CLOSE, OPEN, DONE, CLEAR
    }

    private val _filterBtnType = MutableLiveData(FilterBtnType.CLOSE)
    val filterBtnType: LiveData<FilterBtnType>
        get() = _filterBtnType

    fun setFilterBtnType(type: FilterBtnType) {
        _filterBtnType.value = type
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

    enum class FilterType {
        AREA, DAY, STATUS, PRICE
    }

    private val _priceType = MutableLiveData(PriceType.ALL)

    val priceType: LiveData<PriceType>
        get() = _priceType

    fun setPriceType(type: PriceType) {
        _priceType.value = type
    }

    enum class PriceType(val res: Int) {
        EMPTY(R.string.category_detail_filter_reservation_price),
        ALL(R.string.category_detail_filter_price_all),
        ONE(R.string.category_detail_filter_price_1),
        FOUR(R.string.category_detail_filter_price_4),
        SEVEN(R.string.category_detail_filter_price_7),
        TEN(R.string.category_detail_filter_price_10),
        OVER(R.string.category_detail_filter_price_over)
    }

    fun chipOnclick(type: FilterType) {
        setFilterType(type)
        setFilterBtnType(FilterBtnType.OPEN)
    }

    private val _area = MutableLiveData(Common.areaList)

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

    private var _isPlaned = MutableLiveData(false)
    val isPlaned: LiveData<Boolean>
        get() = _isPlaned

    private var _isDuring = MutableLiveData(false)
    val isDuring: LiveData<Boolean>
        get() = _isDuring


    private val _isFinished = MutableLiveData(false)
    val isFinished: LiveData<Boolean>
        get() = _isFinished

    private var _status = MutableLiveData(Status.EMPTY)
    val status: LiveData<Status>
        get() = _status

    private var _statusList = MutableLiveData<List<Status>>()
    val statusList: LiveData<List<Status>> = _statusList

    val filterArea: LiveData<Boolean>
        get() = _filterArea

    val filterDay: LiveData<Boolean>
        get() = _filterDay

    val filterStatus: LiveData<Boolean>
        get() = _filterStatus

    val filterPrice: LiveData<Boolean>
        get() = _filterPrice

    private var _filterAreaData = MutableLiveData<ArrayList<Areas>>()
    val filterAreaData: LiveData<ArrayList<Areas>>
        get() = _filterAreaData

    private var _filterPriceData = MutableLiveData<String?>()
    val filterPriceData: LiveData<String?>
        get() = _filterPriceData

    private var _selectedDay = MutableLiveData<String?>()
    val selectedDay: LiveData<String?>
        get() = _selectedDay

    private val mutableList = mutableListOf<Status>()

    fun isChecked(status: Status) {
        when (status) {
            Status.EMPTY -> {
                mutableList.clear()
                _isPlaned.value = false
                _isDuring.value = false
                _isFinished.value = false
            }

            Status.ONGOING -> {
                if (_isPlaned.value == false) {
                    _isPlaned.value = true
                    mutableList.add(status)
                } else {
                    _isPlaned.value = false
                    mutableList.remove(status)
                }
            }

            Status.UPCOMING -> {
                if (_isDuring.value == false) {
                    _isDuring.value = true
                    mutableList.add(status)
                } else {
                    _isDuring.value = false
                    mutableList.remove(status)
                }
            }

            Status.COMPLETED -> {
                if (_isFinished.value == false) {
                    _isFinished.value = true
                    mutableList.add(status)
                } else {
                    _isFinished.value = false
                    mutableList.remove(status)
                }
            }
        }
        _statusList.value = mutableList
    }

    fun isShowFilterArea(isShow: Boolean) = _filterArea.postValue(isShow)
    fun isShowFilterDay(isShow: Boolean) = _filterDay.postValue(isShow)
    fun isShowFilterStatus(isShow: Boolean) = _filterStatus.postValue(isShow)
    fun isShowFilterPrice(isShow: Boolean) = _filterPrice.postValue(isShow)

    fun setFilterAreaList(list: ArrayList<Areas>) = _filterAreaData.postValue(list)
    fun setFilterPrice(price: String) = _filterPriceData.postValue(price)
    fun clearFilterPrice() {
        _filterPriceData.value = null
    }

    fun setSelectedDay(day: String?) = _selectedDay.postValue(day)
    fun clearSelectedDay() {
        _selectedDay.value = null
    }

    enum class Status(val res: Int) {
        EMPTY(R.string.category_detail_filter_progress_status),
        ONGOING(R.string.category_detail_filter_planned_performance),
        UPCOMING(R.string.category_detail_filter_during_performance),
        COMPLETED(R.string.category_detail_filter_finish_performance)
    }

}

