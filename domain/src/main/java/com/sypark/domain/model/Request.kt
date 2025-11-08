package com.sypark.domain.model

@Deprecated("")
data class Request(
    val genre: String,
    val order: String,
    val pageIndex: String,
    val size: String? = null
)