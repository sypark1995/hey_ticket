package com.sypark.openTicket.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.Content
import com.sypark.domain.repository.TicketRepository
import com.sypark.openTicket.R
import com.sypark.openTicket.util.UserPreferencesDataStore
import com.sypark.openTicket.view.MainActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

const val NOTIFICATION_CHANNEL_ID = "new_performance"
const val EXTRA_PERFORMANCE_ID = "extra_performance_id"

@HiltWorker
class NotificationMatchWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val ticketRepository: TicketRepository,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        if (!userPreferencesDataStore.getNotificationsEnabled()) {
            return Result.success()
        }

        val genres = userPreferencesDataStore.getInterestedGenres()
        val areas = userPreferencesDataStore.getInterestedAreas()

        if (genres.isEmpty() && areas.isEmpty()) {
            return Result.success()
        }

        val genreBuckets = if (genres.isEmpty()) listOf<String?>(null) else genres.toList()
        val areaBuckets = if (areas.isEmpty()) listOf<String?>(null) else areas.toList()

        val allFetched = mutableListOf<Content>()
        for (genre in genreBuckets) {
            for (area in areaBuckets) {
                allFetched += fetchOrEmpty(genre, area)
            }
        }

        val alreadyNotified = userPreferencesDataStore.getNotifiedIds()
        val newMatches = allFetched
            .distinctBy { it.id }
            .filterNot { alreadyNotified.contains(it.id) }

        if (newMatches.isNotEmpty()) {
            showNotification(newMatches)
        }

        userPreferencesDataStore.markAsNotified(allFetched.map { it.id }.toSet())

        return Result.success()
    }

    private suspend fun fetchOrEmpty(genreCode: String?, areaCode: String?): List<Content> {
        var result: List<Content> = emptyList()
        ticketRepository.getMatchingNew(genreCode, areaCode, rows = 20).collect {
            if (it is ApiResult.Success) {
                result = it.value
            }
        }
        return result
    }

    private fun showNotification(matches: List<Content>) {
        val context = applicationContext
        createNotificationChannel(context)

        val (title, contentIntent) = if (matches.size == 1) {
            val performance = matches.first()
            "${performance.title} 공연이 새로 등록되었어요" to buildDetailIntent(context, performance.id)
        } else {
            "${matches.size}개의 관심 공연이 새로 등록되었어요" to buildHomeIntent(context)
        }

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.icon_noti)
            .setContentTitle(title)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }

    private fun buildDetailIntent(context: Context, performanceId: String): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(EXTRA_PERFORMANCE_ID, performanceId)
        }
        return PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun buildHomeIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(
            context, 1, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "관심 공연 알림",
                NotificationManager.IMPORTANCE_DEFAULT,
            )
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 1001
    }
}
