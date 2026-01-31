package com.sypark.data.db

import androidx.room.*
import com.sypark.data.db.entity.SearchWord
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchWordDao {

    @Query("SELECT * FROM word_table ORDER BY registerDate DESC")
    fun getAlphabetizedWords(): Flow<List<SearchWord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: SearchWord)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

    @Query("DELETE FROM word_table where word IN (SELECT word FROM word_table ORDER BY registerDate LIMIT 1)")
    suspend fun deleteFirstItem()

//    @Delete
//    suspend fun deleteWord()

}