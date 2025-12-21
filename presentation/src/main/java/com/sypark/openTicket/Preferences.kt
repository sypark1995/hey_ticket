package com.sypark.openTicket

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

//todo_sypark dataStore로 변경
object Preferences {
    private const val FILENAME = "com.sypark.openTicket"
    private lateinit var preferences: SharedPreferences


    private val PREF_SORT_INDEX = "sortIndex"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE)
    }

    var sortPosition: Int
        get() = preferences.getInt(PREF_SORT_INDEX, 1)
        set(value) = preferences.edit().putInt(PREF_SORT_INDEX, value).apply()
}