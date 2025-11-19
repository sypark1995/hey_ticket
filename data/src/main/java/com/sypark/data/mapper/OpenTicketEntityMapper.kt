//package com.sypark.data.mapper
//
//import com.sypark.data.db.entity.OpenTicketEntity
//import com.sypark.domain.model.OpenTicket
//
//object OpenTicketEntityMapper : EntityMapper<List<OpenTicket>, List<OpenTicketEntity>> {
//    override fun asEntity(domain: List<OpenTicket>): List<OpenTicketEntity> {
//        return domain.map {
//            OpenTicketEntity(
//                id = it.id,
//                title = it.title,
//                ticket_open_date = it.ticket_open_date,
//                hits = it.hits,
//                image_url = it.image_url
//            )
//        }
//    }
//
//    override fun asDomain(entity: List<OpenTicketEntity>): List<OpenTicket> {
//        return entity.map {
//            OpenTicket(
//                id = it.id,
//                title = it.title,
//                ticket_open_date = it.ticket_open_date,
//                hits = it.hits,
//                image_url = it.image_url
//            )
//        }
//    }
//}
//
//fun List<OpenTicket>.asEntity(): List<OpenTicketEntity> {
//    return OpenTicketEntityMapper.asEntity(this)
//}
//
//fun List<OpenTicketEntity>.asDomain(): List<OpenTicket> {
//    return OpenTicketEntityMapper.asDomain(this)
//}