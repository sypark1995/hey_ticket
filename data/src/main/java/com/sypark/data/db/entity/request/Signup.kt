package com.sypark.data.db.entity.request

import java.io.Serializable

data class Signup(
    val email: String = "",
    val password: String? = "",
    val verificationCode: String? = "",
    val genres: ArrayList<String>? = arrayListOf(),
    val areas: ArrayList<String>? = arrayListOf(),
    val keywords: ArrayList<String>? = arrayListOf(),
    val keywordPush: Boolean? = false
) : Serializable