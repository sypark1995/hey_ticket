package com.sypark.data.db.entity

data class TicketDetail(
    val id: String,

    val title: String,

    val startDate: String,

    val endDate: String,

    val theater: String,

    val cast: String,

    val crew: String,

    val runtime: String,

    val age: String,

    val company: String,

    val price: String,

    val poster: String,

    val story: String,

    val genre: String,

    val status: String,

    val openRun: Boolean,

    val storyUrls: List<String>,

    val schedule: String,

    val views: Long,
)
