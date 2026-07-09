package com.sypark.openTicket

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk
import com.sypark.openTicket.util.AppPreference
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    companion object {

        @SuppressLint("StaticFieldLeak")
        @get:Synchronized
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()

        Preferences.init(this)

        context = applicationContext
        AppPreference.init(this)
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
//        LocalDB.getInstance(context)
    }
}