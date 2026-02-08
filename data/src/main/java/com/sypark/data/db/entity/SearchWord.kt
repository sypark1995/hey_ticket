package com.sypark.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.sypark.data.db.RoomTypeConverter
import java.io.Serializable
import java.util.*

@Entity(tableName = "word_table")
data class SearchWord(

    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String,

    @TypeConverters(RoomTypeConverter::class)
    val registerDate: Date
) : Serializable