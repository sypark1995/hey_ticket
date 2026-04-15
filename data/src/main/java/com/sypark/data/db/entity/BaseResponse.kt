package com.sypark.data.db.entity

import com.google.gson.JsonElement
import java.io.Serializable

data class BaseResponse(
    val code: String,
    val message: String,
    val data: JsonElement?
) : Serializable