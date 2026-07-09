package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.GenreCount
import com.sypark.domain.usecase.GetGenreCountUseCase
import com.sypark.openTicket.Common
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getGenreCountUseCase: GetGenreCountUseCase
) : BaseViewModel() {
    private var mutableGenreCountList = MutableLiveData<List<GenreCount>>()
    val genreCountList: LiveData<List<GenreCount>> = mutableGenreCountList

    suspend fun getGenreCountList() {
        getGenreCountUseCase(Common.genreList.filterNot { it.code == "KID" }.map { it.code }).collect { result ->
            if (result is ApiResult.Success) {
                mutableGenreCountList.value = result.value.filter { it.count > 0 }
            }
        }
    }
}
