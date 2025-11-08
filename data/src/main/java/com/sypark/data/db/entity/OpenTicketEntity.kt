package com.sypark.data.db.entity

import androidx.room.Entity

@Entity
data class OpenTicketEntity(
    var id: Int,

    var title: String,

    var ticket_open_date: String,

    var hits: String,

    var image_url: String? = null,
)