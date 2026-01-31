package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sypark.data.db.entity.SearchWord
import com.sypark.data.repository.SearchRepository
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CategorySearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : BaseViewModel() {

    private var _searchWord = MutableLiveData<String>()
    val searchWord: LiveData<String>
        get() = _searchWord

    /** insert시 count 구해서 10개 이상이면 가장 처음 인덱스 삭제후 데이터 넣기
     * */
    fun insertWord(data: String, registerDate: Date) {
        viewModelScope.launch {
            if (allWords.value!!.size == 10) {
                searchRepository.deleteFirstItem()
                searchRepository.insert(SearchWord(data, registerDate))
            } else {
                searchRepository.insert(SearchWord(data, registerDate))
            }
        }
    }

    fun deleteAllWords() {
        viewModelScope.launch {
            searchRepository.delete()
        }
    }

    fun setWord(word: String) {
        _searchWord.postValue(word)
    }

    val allWords: LiveData<List<SearchWord>> = searchRepository.selectAllWords().asLiveData()

}