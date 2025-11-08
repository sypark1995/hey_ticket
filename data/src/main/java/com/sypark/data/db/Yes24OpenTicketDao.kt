package com.sypark.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sypark.data.db.entity.OpenTicketEntity

@Dao
interface Yes24OpenTicketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertYes24Ticket()

    @Query("SELECT * FROM OpenTicketEntity")
    suspend fun getYes24OpenTicketList(): List<OpenTicketEntity>
}