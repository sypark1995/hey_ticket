package com.sypark.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sypark.data.db.entity.OpenTicket

@Dao
interface MelonOpenTicketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMelonTicket(openTicketEntity: OpenTicket)

    @Query("SELECT * FROM OpenTicket")
    suspend fun getMelonOpenTicketList(): List<OpenTicket>
}