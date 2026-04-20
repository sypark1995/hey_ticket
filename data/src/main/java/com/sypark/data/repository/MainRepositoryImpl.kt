package com.sypark.data.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.sypark.data.db.InterParkOpenTicketDao
import com.sypark.data.service.OpenTicketClient
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val openTicketClient: OpenTicketClient,
    private val interParkOpenTicketDao: InterParkOpenTicketDao,
//    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : MainRepository {
    override suspend fun getRankingTicket(
        timePeriod: String?,
        boxOfficeGenre: String?,
        boxOfficeArea: String?,
        page: Int?,
        pageSize: Int?
    ) = flow {
        // 생산자 , producer
        val data = openTicketClient.requestPerformancesRanking(
            timePeriod,
            boxOfficeGenre,
            boxOfficeArea,
            page,
            pageSize
        )
        emit(data)
    }.onStart { }

    override suspend fun getNewTicket(genre: String?, page: Int?, pageSize: Int?) = flow {
        val data = openTicketClient.requestPerformanceNew(
            genre, page, pageSize
        )

        emit(data)
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
                Log.e("!!!!", it.toString())
            }

        } else {
            emit(list)
        }
    }.onStart { onStart() }.onCompletion { onComplete() }

}