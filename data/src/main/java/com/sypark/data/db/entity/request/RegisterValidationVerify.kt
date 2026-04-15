package com.sypark.data.db.entity.request

import java.io.Serializable

data class RegisterValidationVerify(
    val email: String,
    val code: String
) : Serializable