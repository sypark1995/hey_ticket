package com.sypark.data.db.entity.request

import java.io.Serializable

data class RegisterValidationSend(
    val email: String,
    val verificationType: String
) : Serializable