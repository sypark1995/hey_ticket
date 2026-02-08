package com.sypark.openTicket

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import java.net.HttpURLConnection
import java.net.URL

object BaseUtil {
    fun convertBitmapFromURL(url: String): Bitmap? {
        try {
            val url = URL(url)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(input)

            return bitmap
        } catch (e: Exception) {
            Log.e("convertBitmapFromURL", e.toString())
        }
        return null
    }

    fun mergeBitmapsVertical(bitmaps: List<Bitmap>): Bitmap {
        // 모든 비트맵 이미지의 너비와 높이를 계산합니다.

        val totalWidth = bitmaps.maxOfOrNull {
            it.width
        } ?: 0
        val totalHeight = bitmaps.sumOf { it.height }
        Log.e("totalWidth",totalWidth.toString())
        Log.e("totalHeight",totalHeight.toString())
        // 새로운 비트맵을 생성합니다.
        val mergedBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888)

        // 새로운 비트맵에 모든 비트맵 이미지를 그립니다.
        val canvas = Canvas(mergedBitmap)
        var currentHeight = 0
        for (bitmap in bitmaps) {
            canvas.drawBitmap(bitmap, 0f, currentHeight.toFloat(), null)
            currentHeight += bitmap.height
        }

        return mergedBitmap
    }
}