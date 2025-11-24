package com.sypark.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sypark.data.db.entity.OpenTicket

@Dao
interface Yes24OpenTicketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertYes24Ticket(openTicket: OpenTicket)

    @Query("SELECT * FROM OpenTicket")
    suspend fun getYes24OpenTicketList(): List<OpenTicket>
}