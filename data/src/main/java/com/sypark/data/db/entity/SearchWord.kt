package com.sypark.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class SearchWord(

    @PrimaryKey @ColumnInfo(name = "word")
    val word: String
)