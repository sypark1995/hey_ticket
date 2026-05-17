package com.sypark.openTicket.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sypark.data.db.entity.Content
import com.sypark.data.db.entity.SearchWord
import com.sypark.data.paging.PagingRepository
import com.sypark.data.repository.SearchRepository
import com.sypark.data.util.Util
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val pageRepository: PagingRepository
) : BaseViewModel() {

    private var _searchWord = MutableLiveData<String>()
    val searchWord: LiveData<String>
        get() = _searchWord

    private val _radioButton = MutableLiveData(Util.SearchType.PERFORMANCE)

    val radioButton: LiveData<Util.SearchType>
        get() = _radioButton

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


    fun setRadioButton(type: Util.SearchType) {
        _radioButton.value = type
    }

    val allWords: LiveData<List<SearchWord>> = searchRepository.selectAllWords().asLiveData()

//    fun setSearch(query: String, searchType: Util.SearchType): Flow<PagingData<Content>> {
//        return pageRepository.getSearchPagingData(
//            query = query,
//            searchType = searchType
//        ).cachedIn(viewModelScope)
//    }

    fun setSearch(query: String, searchType: Util.SearchType): Flow<PagingData<Pair<Content, Long>>> {
        return pageRepository.getSearchPagingData2(
            query = query,
            searchType = searchType
        ).cachedIn(viewModelScope)
    }

}