package com.sypark.data.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.sypark.data.db.InterParkOpenTicketDao
import com.sypark.data.db.MelonOpenTicketDao
import com.sypark.data.db.entity.OpenTicket
import com.sypark.data.service.OpenTicketClient
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val openTicketClient: OpenTicketClient,
    private val interParkOpenTicketDao: InterParkOpenTicketDao,
    private val melonOpenTicketDao: MelonOpenTicketDao,
//    private val yes24OpenTicketDao: Yes24OpenTicketDao,
//    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : MainRepository {

    override fun getMelonOpenTicket(
        genre: String,
        order: String,
        pageIndex: String,
        size: String?,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        val list = melonOpenTicketDao.getMelonOpenTicketList()

        if (list.isEmpty()) {
            runCatching {
                openTicketClient.requestMelonTicket(genre, order, pageIndex, size)
            }.onSuccess {
                if (it == null) {
                    onError("")
                } else {
                    it.forEach { item ->
                        melonOpenTicketDao.insertMelonTicket(item)
                    }
                }
                emit(melonOpenTicketDao.getMelonOpenTicketList())
            }.onFailure {
                Log.e("!!!!",it.toString())
            }
        } else {
            emit(list)
        }
    }.onStart { onStart() }.onCompletion { onComplete() }

    @WorkerThread
    override suspend fun getInterParkOpenTicket(
        genre: String,
        order: String,
        pageIndex: String,
        size: String?,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        val list = interParkOpenTicketDao.getAllInterParkOpenTicketList()

        if (list.isEmpty()) {
            runCatching {
                openTicketClient.requestInterParkTicket(genre, order, pageIndex, size)
            }.onSuccess { it ->
                if (it == null) {
                    onError("")
                } else {
                    it.forEach { item ->
                        interParkOpenTicketDao.insertInterParkTicket(item)
                    }
                }
                emit(interParkOpenTicketDao.getAllInterParkOpenTicketList())
            }.onFailure {
                Log.e("!!!!", it.toString())
            }

        } else {
            emit(list)
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
    }.onStart { onStart() }.onCompletion { onComplete() }


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