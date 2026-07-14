package com.sypark.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AreaCodeMapperTest {

    @Test
    fun `toSigngucode maps each app area code to its KOPIS signgucode`() {
        assertEquals("11", AreaCodeMapper.toSigngucode("SEOUL"))
        assertEquals("26", AreaCodeMapper.toSigngucode("BUSAN"))
        assertEquals("27", AreaCodeMapper.toSigngucode("DAEGU"))
        assertEquals("28", AreaCodeMapper.toSigngucode("INCHEON"))
        assertEquals("29", AreaCodeMapper.toSigngucode("GWANGJU"))
        assertEquals("30", AreaCodeMapper.toSigngucode("DAEJEON"))
        assertEquals("31", AreaCodeMapper.toSigngucode("ULSAN"))
        assertEquals("36", AreaCodeMapper.toSigngucode("SEJONG"))
        assertEquals("41", AreaCodeMapper.toSigngucode("GYEONGGI"))
        assertEquals("51", AreaCodeMapper.toSigngucode("GANGWON"))
        assertEquals("43", AreaCodeMapper.toSigngucode("CHUNGBUK"))
        assertEquals("44", AreaCodeMapper.toSigngucode("CHUNGNAM"))
        assertEquals("45", AreaCodeMapper.toSigngucode("JEONBUK"))
        assertEquals("46", AreaCodeMapper.toSigngucode("JEONNAM"))
        assertEquals("47", AreaCodeMapper.toSigngucode("GYEONGBUK"))
        assertEquals("48", AreaCodeMapper.toSigngucode("GYEONGNAM"))
        assertEquals("50", AreaCodeMapper.toSigngucode("JEJU"))
    }

    @Test
    fun `toSigngucode returns null for blank, null and unknown codes`() {
        assertNull(AreaCodeMapper.toSigngucode(""))
        assertNull(AreaCodeMapper.toSigngucode(null))
        assertNull(AreaCodeMapper.toSigngucode("NOT_A_REAL_AREA"))
    }
}
