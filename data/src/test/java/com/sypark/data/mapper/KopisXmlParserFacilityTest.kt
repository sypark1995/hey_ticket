package com.sypark.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Test

class KopisXmlParserFacilityTest {

    @Test
    fun `parseFacility extracts address, coordinates and phone`() {
        val xml = """
            <dbs>
              <db>
                <fcltynm>블루스퀘어 신한카드홀</fcltynm>
                <adres>서울특별시 용산구 이태원로 294</adres>
                <la>37.53987</la>
                <lo>126.99931</lo>
                <telno>02-1552-1369</telno>
              </db>
            </dbs>
        """.trimIndent()

        val result = KopisXmlParser.parseFacility(xml)

        assertEquals("서울특별시 용산구 이태원로 294", result.address)
        assertEquals(37.53987, result.latitude, 0.00001)
        assertEquals(126.99931, result.longitude, 0.00001)
        assertEquals("02-1552-1369", result.phoneNumber)
    }
}
