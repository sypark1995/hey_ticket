package com.sypark.openTicket.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InterParkDto(

    @PrimaryKey
    var subject: String,

    var type: String,

    var date: String,

    var count: String
)