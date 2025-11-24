package com.sypark.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sypark.data.db.entity.OpenTicket

@Dao
interface InterParkOpenTicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInterParkTicket(openTicketEntity: OpenTicket)

    @Query("SELECT * FROM OpenTicket")
    suspend fun getAllInterParkOpenTicketList(): List<OpenTicket>

    @Query("SELECT * FROM OpenTicket order by hits asc limit 10")
    suspend fun getHitsInterParkList(): List<OpenTicket>

}