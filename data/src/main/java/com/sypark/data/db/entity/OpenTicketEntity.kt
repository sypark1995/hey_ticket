package com.sypark.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OpenTicketEntity(

    @PrimaryKey
    var id: Int,

    var title: String,

    var ticket_open_date: String,

    var hits: String,

    var image_url: String? = null,
)