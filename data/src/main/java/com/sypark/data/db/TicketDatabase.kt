package com.sypark.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sypark.data.db.entity.OpenTicket
import com.sypark.data.db.entity.SearchWord

@Database(entities = [SearchWord::class, OpenTicket::class], version = 1, exportSchema = true)
@TypeConverters(RoomTypeConverter::class)
abstract class TicketDatabase : RoomDatabase() {

    abstract fun interParkDao(): InterParkOpenTicketDao
    abstract fun melonDao(): MelonOpenTicketDao
    abstract fun yes24Dao(): Yes24OpenTicketDao

    abstract fun searchWordDao(): SearchWordDao
}