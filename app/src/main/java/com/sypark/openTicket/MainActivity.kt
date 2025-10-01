package com.sypark.openTicket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.sypark.openTicket.dao.InterParkDao
import com.sypark.openTicket.network.BaseUrlUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btn)

        btn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                getInterParkData("0")
            }
        }
    }

    val interParkList = ArrayList<InterParkDao>()

    private fun getInterParkData(page: String) {
        val lastUrl = "bbsno=0&pageno=${page}&stext=&KindOfGoods=&Genre=&sort="
        val url = BaseUrlUtil.interParkUrl + lastUrl

        try {
//            val doc = Jsoup.connect(url).timeout(1000 * 10).get()
            val doc = Jsoup.parse(URL(url).openStream(), "euc-kr", url)

            val data = doc.select(".table").select("div").select("tbody").select("tr")
            var type = ""
            for (item in data) {
                interParkList
                type = item.select(".type").text()
            }
            Log.e("!!!", type)
            Toast.makeText(this, type, Toast.LENGTH_LONG).show()
        } catch (e: java.lang.Exception) {

        }
    }
}