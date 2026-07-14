package com.sypark.data.mapper

object AreaCodeMapper {
    // KOPIS 공식 코드표(공연예술통합전산망OpenAPI공통코드.pdf, "지역(시도)코드 (행정표준코드 앞 2자리)") 기준.
    private val SIGNGUCODE_MAP = mapOf(
        "SEOUL" to "11",
        "BUSAN" to "26",
        "DAEGU" to "27",
        "INCHEON" to "28",
        "GWANGJU" to "29",
        "DAEJEON" to "30",
        "ULSAN" to "31",
        "SEJONG" to "36",
        "GYEONGGI" to "41",
        "GANGWON" to "51",
        "CHUNGBUK" to "43",
        "CHUNGNAM" to "44",
        "JEONBUK" to "45",
        "JEONNAM" to "46",
        "GYEONGBUK" to "47",
        "GYEONGNAM" to "48",
        "JEJU" to "50",
    )

    fun toSigngucode(appAreaCode: String?): String? = appAreaCode?.let { SIGNGUCODE_MAP[it] }
}
