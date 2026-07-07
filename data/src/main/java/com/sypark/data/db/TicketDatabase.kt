package com.sypark.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sypark.data.db.entity.SearchWord

@Database(entities = [SearchWord::class], version = 2, exportSchema = true)
@TypeConverters(RoomTypeConverter::class)
abstract class TicketDatabase : RoomDatabase() {

    abstract fun searchWordDao(): SearchWordDao
}
