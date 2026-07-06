package com.sypark.domain.model

data class Data(
    val contents: List<Content>,
    val page: Long,
    val pageSize: Long,
    val totalPages: Long,
)
