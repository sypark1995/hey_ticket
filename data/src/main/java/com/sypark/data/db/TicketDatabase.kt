package com.sypark.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sypark.data.db.entity.OpenTicketEntity

@Database(entities = [OpenTicketEntity::class], version = 1, exportSchema = true)
abstract class TicketDatabase : RoomDatabase() {

    abstract fun interParkDao(): InterParkOpenTicketDao
    abstract fun melonDao(): MelonOpenTicketDao
    abstract fun yes24Dao(): Yes24OpenTicketDao
}