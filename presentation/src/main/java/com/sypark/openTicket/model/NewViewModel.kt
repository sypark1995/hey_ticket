package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sypark.data.db.entity.Content
import com.sypark.data.paging.PagingRepository
import com.sypark.data.util.Util
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NewViewModel @Inject constructor(
    private val repository: PagingRepository
) : BaseViewModel() {
    private val _genre = MutableLiveData<String?>()
    val genre: LiveData<String?> = _genre

    private val _isShow = MutableLiveData(false)
    val isShow: LiveData<Boolean> = _isShow

    private val _radioButton = MutableLiveData(Util.NewButtonType.CREATED_DATE)

    val radioButton: LiveData<Util.NewButtonType>
        get() = _radioButton

    fun setRadioButton(type: Util.NewButtonType) {
        _radioButton.value = type
    }

    fun setNew(genre: String, sortType: Util.NewButtonType): Flow<PagingData<Content>> {
        return repository.getNewPagingData(
            genre = genre,
            sortType = sortType
        ).cachedIn(viewModelScope)
    }

    fun setGenre(genre: String? = "") {
        _genre.value = genre
    }

    fun setShow(isShow: Boolean) {
        _isShow.value = isShow
    }


}