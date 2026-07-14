package com.sypark.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Test

class KopisXmlParserTest {

    @Test
    fun `parsePerformanceList maps db elements to Content list`() {
        val xml = """
            <dbs>
              <db>
                <mt20id>PF223939</mt20id>
                <prfnm>위키드</prfnm>
                <prfpdfrom>2026.07.01</prfpdfrom>
                <prfpdto>2026.09.30</prfpdto>
                <fcltynm>블루스퀘어 신한카드홀</fcltynm>
                <poster>http://kopis.or.kr/upload/poster1.jpg</poster>
                <genrenm>뮤지컬</genrenm>
                <openrun>N</openrun>
                <prfstate>공연중</prfstate>
              </db>
            </dbs>
        """.trimIndent()

        val result = KopisXmlParser.parsePerformanceList(xml)

        assertEquals(1, result.size)
        assertEquals("PF223939", result[0].id)
        assertEquals("위키드", result[0].title)
        assertEquals("블루스퀘어 신한카드홀", result[0].theater)
        assertEquals(false, result[0].openRun)
        assertEquals("공연중", result[0].state)
    }

    @Test
    fun `parsePerformanceDetail maps single db element with styurls`() {
        val xml = """
            <dbs>
              <db>
                <mt20id>PF223939</mt20id>
                <mt10id>FC000001</mt10id>
                <prfnm>위키드</prfnm>
                <prfpdfrom>2026.07.01</prfpdfrom>
                <prfpdto>2026.09.30</prfpdto>
                <fcltynm>블루스퀘어 신한카드홀</fcltynm>
                <prfcast>정선아, 손승연</prfcast>
                <prfcrew>이지나</prfcrew>
                <prfruntime>165분</prfruntime>
                <prfage>8세 이상</prfage>
                <entrpsnmH>신시컴퍼니</entrpsnmH>
                <pcseguidance>R석 160,000원 S석 130,000원</pcseguidance>
                <poster>http://kopis.or.kr/upload/poster1.jpg</poster>
                <sty>공연 소개</sty>
                <genrenm>뮤지컬</genrenm>
                <prfstate>공연중</prfstate>
                <styurls>
                  <styurl>http://kopis.or.kr/upload/sty1.jpg</styurl>
                  <styurl>http://kopis.or.kr/upload/sty2.jpg</styurl>
                </styurls>
                <dtguidance>공연 당일 취소 불가</dtguidance>
              </db>
            </dbs>
        """.trimIndent()

        val result = KopisXmlParser.parsePerformanceDetail(xml)

        assertEquals("FC000001", result.placeId)
        assertEquals("R석 160,000원 S석 130,000원", result.price)
        assertEquals(listOf(
            "http://kopis.or.kr/upload/sty1.jpg",
            "http://kopis.or.kr/upload/sty2.jpg",
        ), result.storyUrls)
        assertEquals("공연 당일 취소 불가", result.schedule)
    }
}
