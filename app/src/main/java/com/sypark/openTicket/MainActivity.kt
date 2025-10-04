package com.sypark.openTicket

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sypark.openTicket.dao.InterParkDao
import com.sypark.openTicket.network.BaseUrlUtil
import com.sypark.openTicket.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val interParkList: ArrayList<InterParkDao> = arrayListOf()
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btn)

        btn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                interParkList.clear()
//                for (i in 0..5) {
//                    getInterParkData(i)
//                }

                getMelonData(11)
                Log.e(TAG, "" + interParkList.size)
            }
        }
    }

    private fun getInterParkData(page: Int) {
        val lastUrl = "bbsno=0&pageno=${page}&stext=&KindOfGoods=&Genre=&sort=opendate"
        val url = BaseUrlUtil.interParkUrl + lastUrl

        try {
            val doc = Jsoup.parse(URL(url).openStream(), "euc-kr", url)

            val data = doc.select(".table").select("div").select("tbody").select("tr")
            data.forEachIndexed { _, element ->
                val type = element.select(".type").text()
                val subject = element.select(".type").text()
                val date = element.select(".date").text()
                val count = element.select(".count").text()

                interParkList.add(
                    InterParkDao(
                        type = type,
                        subject = subject,
                        date = date,
                        count = count
                    )
                )
            }

        } catch (e: java.lang.Exception) {
            Log.e(TAG, e.toString())
        }
    }

    private fun getMelonData(page: Int) {
        val lastUrl = "&pageIndex=${page}"
        val url = BaseUrlUtil.melonUrl + lastUrl
        Log.e(TAG,url)
        try {
            val doc = Jsoup.parse(URL(BaseUrlUtil.yes24Url).openStream(), "utf-8", url)
            val data = doc.select(".noti-tbl").select("tr").select("td")
            data.forEachIndexed { index, element ->
                val subject = element.select("")
            }
            Log.e(TAG,data.toString())
//            Log.e(TAG,data.toString())
        } catch (e: java.lang.Exception) {
            Log.e("error",e.toString())
        }
    }

    private suspend fun call() {
        try {
            val data = RetrofitClient.instanceInterPark().requestInterParkList("0")

            Log.e("!!!", data.toString())
        } catch (e: java.lang.Exception) {
            Log.e("!!!!", e.toString())
        }
    }
}