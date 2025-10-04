package com.sypark.openTicket.network

import okhttp3.ResponseBody
import org.jsoup.nodes.Document
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitService {

    @GET("webzine/paper/TPNoticeList_iFrame.asp")
    suspend fun requestInterParkList(
        @Query("pageno") pageno: String,
        @Query("bbsno") bbsno: String? = "0",
        @Query("stext") stext: String? = "",
        @Query("KindOfGoods") KindOfGoods: String? = "",
        @Query("Genre") Genre: String? = "",
        @Query("sort") sort: String? = "",
    ): Document
}
//http://ticket.interpark.com/webzine/paper/TPNoticeList_iFrame.asp?bbsno=0&pageno=0&stext=&KindOfGoods=&Genre=&sort=
//http://ticket.interpark.com/webzine/paper/TPNoticeList_iFrame.asp?pageno=0&bbsno=0&stext=&KindOfGoods=&Genre=&sort=