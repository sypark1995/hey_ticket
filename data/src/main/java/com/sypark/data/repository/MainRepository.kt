package com.sypark.data.repository

import com.sypark.data.db.entity.OpenTicket
import kotlinx.coroutines.flow.Flow

interface MainRepository {

//    suspend fun requestMelonOpenTicket(request: Request): Flow<List<OpenTicket>>
//    suspend fun getInterParkOpenTicket(): Flow<List<OpenTicket>>
//    suspend fun getYes24OpenTicket(request: Request): Flow<List<OpenTicket>>

    suspend fun getMelonOpenTicket(
        genre: String,
        order: String,
        pageIndex: String,
        size: String?,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<OpenTicket>>?

    suspend fun getInterParkOpenTicket(
        genre: String,
        order: String,
        pageIndex: String,
        size: String?,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<OpenTicket>>?

    suspend fun getYes24OpenTicket1(
        genre: String,
        order: String,
        pageIndex: String,
        size: String?,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<OpenTicket>>?

}