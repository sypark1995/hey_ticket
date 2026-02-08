package com.sypark.data.repository

import com.sypark.data.db.entity.PlaceDetail
import com.sypark.data.db.entity.TicketDetail

interface PlaceDetailRepository {

    suspend fun getPlaceDetail(
        mt10id: String,
    ): PlaceDetail?
}