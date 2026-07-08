package com.sypark.openTicket.model

import org.junit.Assert.assertTrue
import org.junit.Test

class BookingVendorTest {

    @Test
    fun `interpark searchUrl encodes performance title`() {
        val url = BookingVendor.INTERPARK.searchUrl("위키드")
        assertTrue(url.startsWith("https://ticket.interpark.com/contents/search?keyword="))
        assertTrue(url.contains("keyword=" + java.net.URLEncoder.encode("위키드", "UTF-8")))
    }

    @Test
    fun `all vendors produce a valid https url`() {
        BookingVendor.values().forEach { vendor ->
            assertTrue(vendor.searchUrl("테스트 공연").startsWith("https://"))
        }
    }
}
