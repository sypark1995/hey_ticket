package com.sypark.openTicket

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
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
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
