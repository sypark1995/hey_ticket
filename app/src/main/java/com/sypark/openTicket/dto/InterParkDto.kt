package com.sypark.openTicket.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "interpark_table")
data class InterParkDto(
    @PrimaryKey
    var type: String,

    var subject: String,

    var date: String,

    var count: String
)