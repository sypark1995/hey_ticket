package com.sypark.data.db.entity

import java.io.Serializable

data class GenreCount(
    val genre: String,
    val count: Int
): Serializable