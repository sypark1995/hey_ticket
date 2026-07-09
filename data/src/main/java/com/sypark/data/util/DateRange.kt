package com.sypark.data.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateRange {
    fun todayToDaysAhead(days: Int): Pair<String, String> {
        val format = SimpleDateFormat("yyyyMMdd", Locale.KOREA)
        val calendar = Calendar.getInstance()
        val start = format.format(calendar.time)
        calendar.add(Calendar.DAY_OF_MONTH, days)
        val end = format.format(calendar.time)
        return start to end
    }

    fun yesterday(): String {
        val format = SimpleDateFormat("yyyyMMdd", Locale.KOREA)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        return format.format(calendar.time)
    }
}
