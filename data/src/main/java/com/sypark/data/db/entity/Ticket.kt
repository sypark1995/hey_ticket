package com.sypark.data.db.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Ticket(
    @SerializedName("mt20id")
    var mt20id: String,

    @SerializedName("mt10id")
    var mt10id: String,

    @SerializedName("title")
    var title: String,

    @SerializedName("startDate")
    var startDate: String,

    @SerializedName("endDate")
    var endDate: String,

    @SerializedName("place")
    var place: String,

    @SerializedName("cast")
    var cast: String,

    @SerializedName("crew")
    var crew: String,

    @SerializedName("runtime")
    var runtime: String,

    @SerializedName("age")
    var age: String,

    @SerializedName("entrpsnm")
    var entrpsnm: String,

    @SerializedName("pcseguidance")
    var pcseguidance: String,

    @SerializedName("poster")
    var poster: String,

    @SerializedName("sty")
    var sty: String,

    @SerializedName("genre")
    var genre: String,

    @SerializedName("state")
    var state: String,

    @SerializedName("openrun")
    var openrun: Boolean,

    @SerializedName("styurls")
    var styurls: ArrayList<Detail>,

    @SerializedName("dtguidance")
    var dtguidance: String
) : Serializable