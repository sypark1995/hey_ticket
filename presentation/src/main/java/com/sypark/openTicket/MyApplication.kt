package com.sypark.openTicket

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.kakao.sdk.common.KakaoSdk
import com.sypark.openTicket.worker.NotificationMatchWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {

    companion object {

        @SuppressLint("StaticFieldLeak")
        @get:Synchronized
        lateinit var context: Context
            private set
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        context = applicationContext
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
//        LocalDB.getInstance(context)

        val periodicWork = PeriodicWorkRequestBuilder<NotificationMatchWorker>(1, TimeUnit.DAYS).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "notification_match_work",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWork,
        )
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
