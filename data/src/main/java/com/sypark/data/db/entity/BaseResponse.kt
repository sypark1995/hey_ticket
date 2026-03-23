package com.sypark.data.db.entity

import java.io.Serializable

data class BaseResponse(
    val code: String,
    val message: String,
    val data: Data
) : Serializable