package com.sypark.data.mapper

object KopisGenreMapper {
    // KOPIS 공식 코드표(공연예술통합전산망OpenAPI공통코드.pdf) 기준.
    // pblprfr(목록/상세 조회)는 shcate, boxoffice(랭킹)는 catecode를 쓰며 서로 다른 값이다.
    private val SHCATE_MAP = mapOf(
        "THEATER" to "AAAA",
        "DANCE" to "BBBC",
        "CLASSIC" to "CCCA",
        "KOREAN_TRADITIONAL_MUSIC" to "CCCC",
        "POPULAR_MUSIC" to "CCCD",
        "MIXED_GENRE" to "EEEA",
        "CIRCUS_AND_MAGIC" to "EEEB",
        "MUSICAL" to "GGGA",
    )

    private val CATECODE_MAP = SHCATE_MAP + mapOf(
        "KID" to "KID",
    )

    // ALL이나 매핑에 없는 코드는 null → 호출부가 필터 없이 전체 조회로 처리한다.
    fun toShcate(appGenreCode: String?): String? = appGenreCode?.let { SHCATE_MAP[it] }

    fun toCatecode(appGenreCode: String?): String? = appGenreCode?.let { CATECODE_MAP[it] }
}
