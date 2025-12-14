package com.sypark.data.db.entity

data class CategoryDetailSort(
    val sort: String
)

enum class SortType(val type: String) {
    RECENCY("최신순"),
    BOOK("최신순"),
    VIEW("조회순"),
    AAAA("기대평순")
}