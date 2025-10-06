package com.sypark.openTicket

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.sypark.openTicket.dao.LocalDB

class MyApplication: Application() {

    companion object {

        @SuppressLint("StaticFieldLeak")
        @get:Synchronized
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        LocalDB.getInstance(context)
    }
}