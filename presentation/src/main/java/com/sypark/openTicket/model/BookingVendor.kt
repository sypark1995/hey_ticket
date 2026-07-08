package com.sypark.openTicket.model

import java.net.URLEncoder

enum class BookingVendor(val displayName: String) {
    INTERPARK("인터파크 티켓"),
    MELON("멜론티켓"),
    YES24("YES24 티켓");

    fun searchUrl(performanceTitle: String): String {
        val encoded = URLEncoder.encode(performanceTitle, "UTF-8")
        return when (this) {
            INTERPARK -> "https://ticket.interpark.com/contents/search?keyword=$encoded"
            MELON -> "https://ticket.melon.com/search/index.htm?searchGb=1&query=$encoded"
            YES24 -> "https://ticket.yes24.com/Perf/Search?searchString=$encoded"
        }
    }
}
