package com.sypark.openTicket.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sypark.openTicket.MyApplication
import com.sypark.openTicket.dto.InterParkDto

@Database(entities = [InterParkDto::class], version = 1 ,exportSchema = true)
abstract class LocalDB: RoomDatabase() {

    abstract fun interParkDao(): InterParkDao

    companion object {

        @Volatile private var instance: LocalDB? = null

        fun getInstance(context: Context): LocalDB {
            return instance ?: synchronized(LocalDB::class) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    LocalDB::class.java,
                    "Local.db"
                ).build()
                    .also { instance = it }
            }
        }

        fun getInstance(): LocalDB {
            return if (instance != null) {
                instance!!
            } else {
                getInstance(MyApplication.context)
            }
        }
    }
}