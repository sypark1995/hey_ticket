package com.sypark.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sypark.data.db.entity.OpenTicketEntity

@Dao
interface MelonOpenTicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMelonTicket()

    @Query("SELECT * FROM OpenTicketEntity")
    suspend fun getMelonOpenTicketList(): List<OpenTicketEntity>
}