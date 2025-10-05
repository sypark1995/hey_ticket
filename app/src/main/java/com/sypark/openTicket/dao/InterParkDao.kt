package com.sypark.openTicket.dao

import androidx.room.Dao
import com.sypark.openTicket.dao.base.BaseDao
import com.sypark.openTicket.dto.InterParkDto
import androidx.room.Query

@Dao
abstract class InterParkDao: BaseDao<InterParkDto> {

    @Query("select * from InterParkDto")
    abstract fun getInterParkData()

    @Query("DELETE FROM InterParkDto")
    abstract fun delete()

    //    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insert(interParkDao: InterParkDao)
//
//    @DELETE
//    suspend fun deleteAll()
}