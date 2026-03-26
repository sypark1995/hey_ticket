package com.sypark.data.db.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TicketDetail(
    @SerializedName("id")
    val id: String,

    @SerializedName("placeId")
    val placeId: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("startDate")
    val startDate: String,

    @SerializedName("endDate")
    val endDate: String,

    @SerializedName("place")
    val place: String,

    @SerializedName("cast")
    val cast: String,

    @SerializedName("crew")
    val crew: String,

    @SerializedName("runtime")
    val runtime: String,

    @SerializedName("age")
    val age: String,

    @SerializedName("company")
    val company: String,

    @SerializedName("price")
    val price: String,

    @SerializedName("poster")
    val poster: String,

    @SerializedName("story")
    val story: String,

    @SerializedName("genre")
    val genre: String,

    @SerializedName("state")
    val state: String,

    @SerializedName("openRun")
    val openRun: Boolean,

    @SerializedName("storyUrls")
    val storyUrls: List<String>,

    @SerializedName("dtguidance")
    val dtguidance: String,

    @SerializedName("views")
    val views: Long,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,
) : Serializable
