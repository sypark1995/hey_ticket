package com.sypark.data.db.entity

data class Data(
    val contents: List<Content>,
    val page: Long,
    val pageSize: Long,
    val totalPages: Long,
)