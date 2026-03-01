package com.sypark.openTicket

import android.os.Build
import androidx.annotation.RequiresApi
import com.sypark.data.db.entity.Genre
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

    val genreList = listOf(
        Genre("AAAA", "전체"),
        Genre("AAAA", "연극"),
        Genre("BBBC", "무용(서양/한국무용)"),
        Genre("BBBE", "대중무용"),
        Genre("CCCA", "클래식(서양음악)"),
        Genre("CCCC", "국악(한국음악)"),
        Genre("CCCD", "대중음악"),
        Genre("EEEA", "복합"),
        Genre("EEEB", "서커스/마술"),
        Genre("GGGA", "뮤지컬")
    )

    val categoryList = listOf(
        Genre("AAAA", "연극"),
        Genre("BBBC", "무용(서양/한국무용)"),
        Genre("BBBE", "대중무용"),
        Genre("CCCA", "클래식(서양음악)"),
        Genre("CCCC", "국악(한국음악)"),
        Genre("CCCD", "대중음악"),
        Genre("EEEA", "복합"),
        Genre("EEEB", "서커스/마술"),
        Genre("GGGA", "뮤지컬")
    )
}