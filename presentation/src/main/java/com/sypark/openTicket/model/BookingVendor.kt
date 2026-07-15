package com.sypark.openTicket.model

import java.net.URLEncoder

enum class BookingVendor(val displayName: String) {
    INTERPARK("인터파크 티켓"),
    MELON("멜론티켓"),
    YES24("YES24 티켓");

    fun searchUrl(performanceTitle: String): String {
        val encoded = URLEncoder.encode(performanceTitle, "UTF-8")
        return when (this) {
            INTERPARK -> "https://tickets.interpark.com/contents/search?q=$encoded"
            MELON -> "https://ticket.melon.com/search/index.htm?q=$encoded"
            YES24 -> "https://ticket.yes24.com/Search/SearchList.aspx?SearchWord=$encoded"
        }
    }
}
