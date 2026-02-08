package com.sypark.data.db.entity

import com.google.gson.annotations.SerializedName

data class PlaceDetail(
    @SerializedName("name")
    var name: String,

    @SerializedName("mt10id")
    var mt10id: String,

    @SerializedName("count")
    var count: Int,

    @SerializedName("fcltychartr")
    var fcltychartr: String,

    @SerializedName("opende")
    var opende: Int,

    @SerializedName("seatscale")
    var seatscale: String,

    @SerializedName("phoneNumber")
    var phoneNumber: String,

    @SerializedName("webUrl")
    var webUrl: String,

    @SerializedName("address")
    var address: String,

    @SerializedName("latitude")
    var latitude: Double,

    @SerializedName("longitude")
    var longitude: Double
)