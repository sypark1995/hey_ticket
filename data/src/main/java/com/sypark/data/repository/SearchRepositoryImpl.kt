package com.sypark.data.repository

import com.sypark.data.db.SearchWordDao
import com.sypark.data.db.entity.SearchWord
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchWordDao: SearchWordDao
) : SearchRepository {
    override suspend fun insert(searchWord: SearchWord) {
        searchWordDao.insert(searchWord)
    }

    override suspend fun delete() {
        searchWordDao.deleteAll()
    }

    override fun selectAllWords(): Flow<List<SearchWord>> {
        return searchWordDao.getAlphabetizedWords()
    }
}