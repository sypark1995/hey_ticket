package com.sypark.openTicket.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import com.sypark.openTicket.dao.base.BaseDao
import com.sypark.openTicket.dto.InterParkDto
import androidx.room.Query

@Dao
abstract class InterParkDao : BaseDao<InterParkDto> {

//    @Query("select * from InterParkDto")
//    abstract fun getInterParkData(): LiveData<List<InterParkDto>>

    @Query("delete from InterParkDto")
    abstract fun deleteAllData()
}