package com.sypark.openTicket

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

object Common {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayOfWeek(string: String): String {
        try {
            val list = string.split("-")

            val date = LocalDate.of(        // ex) LocalDate.of(2021,12,25)
                list[0].toInt(),
                list[1].toInt(),
                list[2].toInt()
            )

            when (date.dayOfWeek.value) {
                1 -> {
                    return "(월)"
                }

                2 -> {
                    return "(화)"
                }

                3 -> {
                    return "(수)"
                }

                4 -> {
                    return "(목)"
                }

                5 -> {
                    return "(금)"
                }

                6 -> {
                    return "(토)"
                }

                7 -> {
                    return "(일)"
                }
            }
        } catch (e: Exception) {
            return ""
        }
        return ""
    }
}