package com.sypark.data.db.entity

data class Content(
    val id: String,
    val placeId: String,
    val title: String,
    val startDate: String,
    val endDate: String,
    val place: String,
    val cast: String,
    val crew: String,
    val runtime: String,
    val age: String,
    val company: String,
    val price: String,
    val poster: String,
    val story: String,
    val genre: String,
    val state: String,
    val openRun: Boolean,
    val storyUrls: List<String>,
    val dtguidance: String,
    val rank: Long,
)