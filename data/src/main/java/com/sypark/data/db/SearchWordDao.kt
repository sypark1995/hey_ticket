package com.sypark.data.db

import androidx.room.*
import com.sypark.data.db.entity.SearchWord
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchWordDao {

    @Query("SELECT * FROM word_table")
    fun getAlphabetizedWords(): Flow<List<SearchWord>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: SearchWord)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

//    @Delete
//    suspend fun deleteWord()

}