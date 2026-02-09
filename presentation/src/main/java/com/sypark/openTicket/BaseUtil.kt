package com.sypark.openTicket

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import java.lang.Boolean
import java.net.HttpURLConnection
import java.net.URL

object BaseUtil {
    fun convertBitmapFromURL(url: String, reqWidth: Int, reqHeight: Int): Bitmap? {
        try {
            val url = URL(url)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(input)
            BitmapFactory.Options().run {
                inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)
                inJustDecodeBounds = false
            }

            return bitmap
        } catch (e: Exception) {
            Log.e("convertBitmapFromURL", e.toString())
        }
        return null
    }


    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    fun mergeBitmapsVertical(bitmaps: List<Bitmap>): Bitmap {
        // 모든 비트맵 이미지의 너비와 높이를 계산합니다.

        val totalWidth = bitmaps.maxOfOrNull {
            it.width
        } ?: 0
        val totalHeight = bitmaps.sumOf { it.height }
        Log.e("totalWidth", totalWidth.toString())
        Log.e("totalHeight", totalHeight.toString())
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

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    fun bitmapResizePrc(
        context: Context,
        Src: Bitmap,
        newHeight: Int,
        newWidth: Int
    ): BitmapDrawable {
        val result: BitmapDrawable
        var width = Src.width
        var height = Src.height

        // calculate the scale - in this case = 0.4f
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height

        // createa matrix for the manipulation
        val matrix = Matrix()
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight)

        // rotate the Bitmap  회전 시키려면 주석 해제!
        //matrix.postRotate(45);

        // recreate the new Bitmap
        val resizedBitmap = Bitmap.createBitmap(Src, 0, 0, width, height, matrix, true)

        // check
        width = resizedBitmap.width
        height = resizedBitmap.height
        Log.i(
            "ImageResize", "Image Resize Result : " + Boolean.toString(
                newHeight == height && newWidth == width
            )
        )

        // make a Drawable from Bitmap to allow to set the BitMap
        // to the ImageView, ImageButton or what ever
        result = BitmapDrawable(context.resources, resizedBitmap)
        return result
    }
}