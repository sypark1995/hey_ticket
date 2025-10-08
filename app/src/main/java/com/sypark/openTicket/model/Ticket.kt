package com.sypark.openTicket.model

data class Ticket(
    val id: Int,
    val imageUrl: String? = "",
    val title: String? = "",
    val date: String? = "",
    val hits: String? = ""
)