package com.sypark.data.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import org.junit.Assert.assertEquals
import org.junit.Test

class DateRangeTest {

    @Test
    fun `yesterday returns the date one day before today in yyyyMMdd format`() {
        val expected = SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(
            Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -1) }.time
        )

        assertEquals(expected, DateRange.yesterday())
    }
}
