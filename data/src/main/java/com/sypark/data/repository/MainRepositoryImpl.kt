package com.sypark.data.repository

import com.sypark.data.AppDispatchers
import com.sypark.data.Dispatcher
import com.sypark.data.db.InterParkOpenTicketDao
import com.sypark.data.db.MelonOpenTicketDao
import com.sypark.data.db.Yes24OpenTicketDao
import com.sypark.data.mapper.asDomain
import com.sypark.data.mapper.asEntity
import com.sypark.data.service.OpenTicketClient
import com.sypark.domain.model.OpenTicket
import com.sypark.domain.repository.MainRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val openTicketClient: OpenTicketClient,
    private val interParkOpenTicketDao: InterParkOpenTicketDao,
//    private val melonOpenTicketDao: MelonOpenTicketDao,
//    private val yes24OpenTicketDao: Yes24OpenTicketDao,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
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
    ) = flow {
        val list = interParkOpenTicketDao.getAllInterParkOpenTicketList().asDomain()

        if (list.isEmpty()) {
            runCatching {
                openTicketClient.requestInterParkTicket(genre, order, pageIndex, size)
            }.onSuccess {
                if (it == null) {
                    onError("")
                } else {
                    it.collect { data ->
                        data.asEntity().forEach { item ->
                            interParkOpenTicketDao.insertInterParkTicket(item)
                        }

                    }

                    emit(interParkOpenTicketDao.getAllInterParkOpenTicketList().asDomain())
                }

            }.onFailure {
                onError(it.toString())
            }
//            try {
//                val response =
//                    openTicketClient.requestInterParkTicket(genre, order, pageIndex, size)
//                response?.collect {
//                    if (it.isEmpty()) {
//                        onError("")
//                    } else {
//                        it.asEntity().forEach { data ->
//                            interParkOpenTicketDao.insertInterParkTicket(data)
//                        }
//
//                        emit(interParkOpenTicketDao.getAllInterParkOpenTicketList().asDomain())
//                    }
//                }
//            } catch (e: Exception) {
//                onError(e.toString())
//            }
        } else {
            emit(list)
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)


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