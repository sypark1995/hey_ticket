package com.sypark.openTicket

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

//todo_sypark dataStore로 변경
object Preferences {
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE)
    }

    var sortPosition: Int
        get() = preferences.getInt(PREFERENCE_SORT_INDEX, 1)
        set(value) = preferences.edit().putInt(PREFERENCE_SORT_INDEX, value).apply()
//
//    var email: String
//        get() = preferences.getString(PREFERENCE_KEY_EMAIL, "")
//        set(value) = preferences.edit().putInt(PREFERENCE_KEY_EMAIL, value).apply()
}