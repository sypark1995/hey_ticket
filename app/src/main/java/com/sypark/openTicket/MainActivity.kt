package com.sypark.openTicket

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sypark.openTicket.dao.LocalDB
import com.sypark.openTicket.dto.InterParkDto
import com.sypark.openTicket.network.BaseUrlUtil
import com.sypark.openTicket.network.RetrofitClient
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private val interParkList: ArrayList<InterParkDto> = arrayListOf()
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btn)

        btn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
//                LocalDB.getInstance().interParkDao().deleteAllData()
                interParkList.clear()
                for (i in 0..5) {
                    getInterParkData(i)
                }
                Log.e(TAG, "" + interParkList.size)
                try {
//                    LocalDB.getInstance().interParkDao().insert(*interParkList.toTypedArray())
                } catch (e: Exception) {
                    Log.e(TAG,e.toString())
                }
//                getMelonData(11)
                Log.e(TAG, "22222")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ISO_LOCAL_TIME
    @RequiresApi(Build.VERSION_CODES.O)
    val time = { formatter.format(LocalDateTime.now()) }

    suspend fun getValue(): Double {
        Log.e("","entering getValue() at ${time()}")
        delay(3000)
        Log.e("","leaving getValue() at ${time()}")
        return Math.random()
    }

    private fun getInterParkData(page: Int) {
        val lastUrl = "bbsno=0&pageno=${page}&stext=&KindOfGoods=&Genre=&sort=opendate"
        val url = BaseUrlUtil.interParkUrl + lastUrl

        try {
            val doc = Jsoup.parse(URL(url).openStream(), "euc-kr", url)

            val data = doc.select(".table").select("div").select("tbody").select("tr")
            data.forEachIndexed { _, element ->
                val type = element.select(".type").text()
                val subject = element.select(".subject").text()
                val date = element.select(".date").text()
                val count = element.select(".count").text()

//                interParkList.add(
//                    InterParkDto(
//                        type = type,
//                        subject = subject,
//                        date = date,
//                        count = count
//                    )
//                )
                LocalDB.getInstance().interParkDao().insert(InterParkDto(
                    type = type,
                    subject = subject,
                    date = date,
                    count = count
                ))
            }

        } catch (e: java.lang.Exception) {
            Log.e(TAG, e.toString())
        }
    }

    private fun getMelonData(page: Int) {
        val lastUrl = "&pageIndex=${page}"
        val url = BaseUrlUtil.melonUrl + lastUrl
        Log.e(TAG, url)
        try {
            val doc = Jsoup.parse(URL(BaseUrlUtil.yes24Url).openStream(), "utf-8", url)
            val data = doc.select(".noti-tbl").select("tr").select("td")
            data.forEachIndexed { index, element ->
                val subject = element.select("")
            }
            Log.e(TAG, data.toString())
//            Log.e(TAG,data.toString())
        } catch (e: java.lang.Exception) {
            Log.e("error", e.toString())
        }
    }

    private suspend fun call() {
        try {
            val data = RetrofitClient.instanceMelon().requestInterParkList("0")

            Log.e("!!!", data.toString())
        } catch (e: java.lang.Exception) {
            Log.e("!!!!", e.toString())
        }
    }
}