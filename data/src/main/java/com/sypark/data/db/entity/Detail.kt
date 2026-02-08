package com.sypark.data.db.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Detail(
    @SerializedName("url")
    var url: String
) : Serializable