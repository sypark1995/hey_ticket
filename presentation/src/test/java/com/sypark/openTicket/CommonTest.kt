package com.sypark.openTicket

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar

class CommonTest {

    @Test
    fun `compareDate returns BEFORE when KOPIS-formatted start date is in the future`() {
        assertEquals(Common.DateType.BEFORE, Common.compareDate("2999.01.01", "2999.01.05"))
    }

    @Test
    fun `compareDate returns FINISH when KOPIS-formatted end date is in the past`() {
        assertEquals(Common.DateType.FINISH, Common.compareDate("2000.01.01", "2000.01.05"))
    }

    @Test
    fun `compareDate returns ERROR for an unparseable date`() {
        assertEquals(Common.DateType.ERROR, Common.compareDate("not-a-date", "2999.01.05"))
    }

    @Test
    fun `getDayOfWeek returns the correct day for a KOPIS-formatted date`() {
        // 2026.07.18 is a Saturday
        assertEquals("(토)", Common.getDayOfWeek("2026.07.18"))
    }

    @Test
    fun `getDayOfWeek returns empty string for an unparseable date`() {
        assertEquals("", Common.getDayOfWeek("2026-07-18"))
    }

    @Test
    fun `genStrDate formats a KOPIS-formatted date with day of week and state`() {
        assertEquals("7월 18일 (토) 시작", Common.genStrDate("2026.07.18", "시작"))
    }

    @Test
    fun `calculateDday returns a positive D-day count for a future KOPIS-formatted date`() {
        val tomorrow = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 1) }.time
        val tomorrowFormatted = SimpleDateFormat("yyyy.MM.dd").format(tomorrow)
        assertEquals("D-1", Common.calculateDday(tomorrowFormatted))
    }

    @Test
    fun `calculateDday returns empty string for an unparseable date`() {
        assertEquals("", Common.calculateDday("not-a-date"))
    }

    @Test
    fun `daysUntil returns the day count for a future KOPIS-formatted date`() {
        val tomorrow = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 1) }.time
        val tomorrowFormatted = SimpleDateFormat("yyyy.MM.dd").format(tomorrow)
        assertEquals(1L, Common.daysUntil(tomorrowFormatted))
    }

    @Test
    fun `daysUntil returns null for an unparseable date`() {
        assertEquals(null, Common.daysUntil("not-a-date"))
    }
}
