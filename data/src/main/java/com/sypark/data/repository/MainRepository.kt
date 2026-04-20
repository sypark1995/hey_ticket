package com.sypark.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.sypark.data.db.entity.BaseResponse
import com.sypark.data.db.entity.OpenTicket
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun getInterParkOpenTicket(
        genre: String,
        order: String,
        pageIndex: String,
        size: String?,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<OpenTicket>>?

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getRankingTicket(
        timePeriod: String?,
        boxOfficeGenre: String?,
        boxOfficeArea: String?,
        page: Int? = 0,
        pageSize: Int? = 10
    ): Flow<BaseResponse>

    suspend fun getNewTicket(
        genre: String?,
        page: Int? = 0,
        pageSize: Int? = 10
    ): Flow<BaseResponse>
}