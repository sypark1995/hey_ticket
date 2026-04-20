package com.sypark.openTicket.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.sypark.openTicket.FILENAME
import com.sypark.openTicket.MyApplication


/** create_by_sypark
 * 안드로이드 애플리케이션에서 공유 프리퍼런스(Shared Preferences)를 관리하기 위한 유틸리티 클래스.
 */
object AppPreference {

    /** Android 애플리케이션의 SharedPreferences 인스턴스 */
    private lateinit var preferences: SharedPreferences

    /** Java 객체를 JSON 문자열로 변환하고 JSON 문자열을 Java 객체로 변환하기 위해 사용되는 Gson 클래스의 인스턴스. */
//    private val gson: Gson = GsonBuilder().registerTypeHierarchyAdapter(DateTime::class.java, DateTimeConverter())
//        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()

    /**
     * AppPreference를 초기화하고 Android SharedPreferences 인스턴스를 설정.
     * Application Class 에서 호출 필요.
     *
     * @param ctx Android Application Context
     */
    fun init(ctx: Context) {
//        preferences = PreferenceManager.getDefaultSharedPreferences(ctx)
        preferences = MyApplication.context.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE)
    }

    /**
     * Preference Value 삭제
     *
     * @param key Preference Key
     */
    fun clear(key: String) {
        preferences.edit()
            .putString(key, null)
            .apply()
    }

    /**
     * Preference Value 설정 (원시 타입)
     *
     * @param key Preference Key
     * @param value Preference Value
     */
    fun set(key: String, value: Any) {
        preferences.edit().apply {
            when (value) {
                is String -> this.putString(key, value)
                is Int -> this.putInt(key, value)
                is Boolean -> this.putBoolean(key, value)
                is Float -> this.putFloat(key, value)
                is Long -> this.putLong(key, value)
                else -> throw Exception("is not define type cast")
            }
        }.apply()
    }

    /**
     * Preference Value 가져옴. (원시 타입)
     *
     * @param key Preference Key
     * @param defValue Preference Value 값이 없을 경우 반환할 기본 값
     * @return Preference Value
     */
    fun <T> get(key: String, defValue: T): T {
        @Suppress("UNCHECKED_CAST")
        return when (defValue) {
            is String -> preferences.getString(key, defValue) as T
            is Int -> preferences.getInt(key, defValue) as T
            is Boolean -> preferences.getBoolean(key, defValue) as T
            is Float -> preferences.getFloat(key, defValue) as T
            is Long -> preferences.getLong(key, defValue) as T

            else -> throw Exception("is not define type cast")
        }
    }

    fun clearAll() {
        preferences.edit().clear().apply()
    }
}