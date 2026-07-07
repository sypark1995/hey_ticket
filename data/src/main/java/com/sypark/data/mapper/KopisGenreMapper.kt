package com.sypark.data.mapper

object KopisGenreMapper {
    // 검증 필요: KOPIS 공식 장르 코드표로 재확인.
    // MUSICAL/THEATER는 KOPIS에 별도 코드가 없어 동일하게 매핑됨(알려진 한계, 개수 합산됨).
    // 매핑에 없는 코드(ALL, KID)는 null 반환 → KOPIS 목록 조회 시 장르 필터 없이 전체 개수로 폴백.
    private val CODE_MAP = mapOf(
        "POPULAR_MUSIC" to "AAAE",
        "MUSICAL" to "AAAA",
        "THEATER" to "AAAA",
        "CLASSIC" to "AAAC",
        "KOREAN_TRADITIONAL_MUSIC" to "AAAD",
        "DANCE" to "AAAB",
        "CIRCUS_AND_MAGIC" to "AAAG",
        "MIXED_GENRE" to "AAAH",
    )

    fun toKopisCode(appGenreCode: String): String? = CODE_MAP[appGenreCode]
}
