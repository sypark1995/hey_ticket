package com.sypark.data.repository

import com.sypark.data.db.entity.SearchWord
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun insert(searchWord: SearchWord)

    suspend fun delete()

    fun selectAllWords(): Flow<List<SearchWord>>
}