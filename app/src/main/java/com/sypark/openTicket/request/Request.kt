package com.sypark.openTicket.request

data class Request(
    val genre: String,
    val order: String,
    val pageIndex: String,
    val size: String? = null
)