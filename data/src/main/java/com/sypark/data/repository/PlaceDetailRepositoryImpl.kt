package com.sypark.data.repository

import com.sypark.data.db.entity.PlaceDetail
import com.sypark.data.service.OpenTicketClient
import javax.inject.Inject

class PlaceDetailRepositoryImpl @Inject constructor(
    private val openTicketClient: OpenTicketClient
) : PlaceDetailRepository {
    override suspend fun getPlaceDetail(mt10id: String): PlaceDetail {
        return openTicketClient.requestPlaceDetail(mt10id)
    }

}