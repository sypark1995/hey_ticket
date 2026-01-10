package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sypark.data.db.entity.SearchWord
import com.sypark.data.repository.SearchRepository
import com.sypark.openTicket.base.BaseViewModel
import com.sypark.openTicket.view.SearchWordListAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategorySearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : BaseViewModel() {

    private var _searchWord = MutableLiveData<String>()
    val searchWord: LiveData<String>
        get() = _searchWord

    private val _editLayoutVisibility = MutableLiveData(false)
    val editLayoutVisibility: LiveData<Boolean>
        get() = _editLayoutVisibility

    fun changeVisibility(isVisibility: Boolean) = viewModelScope.launch {
        _editLayoutVisibility.postValue(isVisibility)
    }

    fun insertWord(data: String) {
        viewModelScope.launch {
            searchRepository.insert(SearchWord(data))
        }
    }

    fun deleteAllWords() {
        viewModelScope.launch {
            searchRepository.delete()
        }
    }
//    fun setWord(word: String) = _searchWord.postValue(word)

    fun setWord(word: String) {
        _searchWord.postValue(word)
    }

    val allWords: LiveData<List<SearchWord>> = searchRepository.selectAllWords().asLiveData()

}