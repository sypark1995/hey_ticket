package com.sypark.openTicket

import android.os.Build
import androidx.annotation.RequiresApi
import com.sypark.data.db.entity.CategoryDetailArea
import com.sypark.data.db.entity.CategoryDetailSort
import com.sypark.data.db.entity.Genre
import java.time.LocalDate
import java.util.regex.Pattern

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

    val categoryDetailAreaList = listOf(
        CategoryDetailArea("서울시"),
        CategoryDetailArea("충청북도"),
        CategoryDetailArea("충청남도"),
        CategoryDetailArea("경기도"),
        CategoryDetailArea("전라북도"),
        CategoryDetailArea("강원도"),
        CategoryDetailArea("전라남도"),
        CategoryDetailArea("경상남도"),
        CategoryDetailArea("경상북도"),
    )

    val sortList = listOf(
        CategoryDetailSort("최근 등록순"),
        CategoryDetailSort("예매순"),
        CategoryDetailSort("조회수순")
    )

    fun setPattern(pw: String): Boolean {
        val pwPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"
        return Pattern.compile(pwPattern).matcher(pw).find()
    }

    val byPass = false

    val recommendAreaList = listOf(
        CategoryDetailArea("서울"),
        CategoryDetailArea("부산"),
        CategoryDetailArea("대구"),
        CategoryDetailArea("인천"),
        CategoryDetailArea("광주"),
        CategoryDetailArea("대전"),
        CategoryDetailArea("울산"),
        CategoryDetailArea("세종"),
        CategoryDetailArea("경기도"),
        CategoryDetailArea("강원도"),
        CategoryDetailArea("충청북도"),
        CategoryDetailArea("충청남도"),
        CategoryDetailArea("전라북도"),
        CategoryDetailArea("전라남도"),
        CategoryDetailArea("경상북도"),
        CategoryDetailArea("경상남도"),
        CategoryDetailArea("제주특별자치도")
    )

    val selectedAreaList = arrayListOf<CategoryDetailArea>()
    val selectedGenreList = arrayListOf<Genre>()
}