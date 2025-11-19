package com.sypark.data.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.sypark.data.AppDispatchers
import com.sypark.data.Dispatcher
import com.sypark.data.db.InterParkOpenTicketDao
import com.sypark.data.db.MelonOpenTicketDao
import com.sypark.data.db.Yes24OpenTicketDao
import com.sypark.data.db.entity.OpenTicketEntity
import com.sypark.data.service.OpenTicketClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val openTicketClient: OpenTicketClient,
    private val interParkOpenTicketDao: InterParkOpenTicketDao,
//    private val melonOpenTicketDao: MelonOpenTicketDao,
//    private val yes24OpenTicketDao: Yes24OpenTicketDao,
//    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : MainRepository {

    override suspend fun getMelonOpenTicket(
        genre: String,
        order: String,
        pageIndex: String,
        size: String?,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<OpenTicketEntity>> {
        return flow {

        }
    }

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
                Log.e("!!!!",it.toString())
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
): Flow<List<OpenTicketEntity>> {
    TODO("Not yet implemented")
}

}