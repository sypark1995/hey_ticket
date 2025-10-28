package com.sypark.openTicket.model

data class MelonTicket(
    var id: Int,
    var imageUrl: String,
    var title: String,
    var openDate: String,
    var registrationDate: String,
//    var tags: ArrayList<String>,
    var hits: String,
)