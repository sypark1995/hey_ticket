package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sypark.data.db.entity.GenreCount
import com.sypark.data.repository.CategoryRepository
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: CategoryRepository
) : BaseViewModel() {
    private var mutableGenreCountList = MutableLiveData<List<GenreCount>>()
    val genreCountList: LiveData<List<GenreCount>> = mutableGenreCountList

    suspend fun getGenreCountList() {
        repository.getGenreCountList().flowOn(Dispatchers.IO).catch {

        }.collect {
            val data = Gson().fromJson<List<GenreCount>>(
                it.data, object : TypeToken<List<GenreCount>>() {}.type
            )
            mutableGenreCountList.value = data
        }

    }
}