package com.sypark.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class OpenTicket(

    @PrimaryKey
    @SerializedName("id")
    var id: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("ticket_opening_date")
    var ticket_opening_date: String,

    @SerializedName("hits")
    var hits: String,

    @SerializedName("image_url")
    var image_url: String? = null,
)