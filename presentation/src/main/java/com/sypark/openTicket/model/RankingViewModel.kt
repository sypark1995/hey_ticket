package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sypark.data.db.entity.Content
import com.sypark.data.repository.PagingRepository
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val repository: PagingRepository
) : BaseViewModel() {
    private val _genre = MutableLiveData<String?>()
    val genre: LiveData<String?> = _genre

    fun setRanking(genre: String): Flow<PagingData<Content>> {
        return repository.getPagingData(
            genre = genre
        ).cachedIn(viewModelScope)
    }

    fun setGenre(genre: String? = "") {
        _genre.value = genre
    }
}