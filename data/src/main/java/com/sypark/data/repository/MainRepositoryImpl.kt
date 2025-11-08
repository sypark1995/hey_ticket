package com.sypark.data.repository

import com.sypark.data.db.InterParkOpenTicketDao
import com.sypark.data.db.MelonOpenTicketDao
import com.sypark.data.db.Yes24OpenTicketDao
import com.sypark.data.mapper.asDomain
import com.sypark.data.service.OpenTicketClient
import com.sypark.domain.model.OpenTicket
import com.sypark.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val openTicketClient: OpenTicketClient,
    private val interParkOpenTicketDao: InterParkOpenTicketDao,
    private val melonOpenTicketDao: MelonOpenTicketDao,
    private val yes24OpenTicketDao: Yes24OpenTicketDao
) : MainRepository {
    override suspend fun getMelonOpenTicket(
        genre: String,
        order: String,
        pageIndex: String,
        size: String?,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<OpenTicket>> {
        return flow {

        }
    }

    override suspend fun getInterParkOpenTicket(
        genre: String,
        order: String,
        pageIndex: String,
        size: String?,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<OpenTicket>> {
        return flow {
            val list = melonOpenTicketDao.getMelonOpenTicketList().asDomain()

            if (list.isEmpty()) {
                openTicketClient.requestInterParkTicket(genre, order, pageIndex, size)
            } else {

            }
        }
    }

    override suspend fun getYes24OpenTicket1(
        genre: String,
        order: String,
        pageIndex: String,
        size: String?,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<OpenTicket>> {
        TODO("Not yet implemented")
    }

}