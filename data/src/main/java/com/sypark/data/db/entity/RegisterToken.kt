package com.sypark.data.db.entity

data class RegisterToken(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String
)