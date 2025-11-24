package com.sypark.data.di

import android.app.Application
import androidx.room.Room
import com.sypark.data.db.InterParkOpenTicketDao
import com.sypark.data.db.MelonOpenTicketDao
import com.sypark.data.db.TicketDatabase
import com.sypark.data.db.Yes24OpenTicketDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application,
    ): TicketDatabase {
        return Room.databaseBuilder(application, TicketDatabase::class.java, "Ticket.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideInterParkTicketDao(appDatabase: TicketDatabase): InterParkOpenTicketDao {
        return appDatabase.interParkDao()
    }

    @Provides
    @Singleton
    fun provideMelonDao(appDatabase: TicketDatabase): MelonOpenTicketDao {
        return appDatabase.melonDao()
    }
//
//    @Provides
//    @Singleton
//    fun provideYes24TicketDao(appDatabase: TicketDatabase): Yes24OpenTicketDao {
//        return appDatabase.yes24Dao()
//    }
}