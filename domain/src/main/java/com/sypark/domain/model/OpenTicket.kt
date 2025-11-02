package com.sypark.domain.model

import com.google.gson.annotations.SerializedName

data class OpenTicket(
    @SerializedName("id")
    var id: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("ticket_open_date")
    var ticket_open_date: String,

    @SerializedName("hits")
    var hits: String,

    @SerializedName("image_url")
    var image_url: String? = null,

    @SerializedName("registration_date")
    var registration_date: String? = null,
)