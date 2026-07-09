package com.sypark.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class KopisGenreMapperTest {

    @Test
    fun `toShcate maps each app genre to its KOPIS shcate code`() {
        assertEquals("AAAA", KopisGenreMapper.toShcate("THEATER"))
        assertEquals("BBBC", KopisGenreMapper.toShcate("DANCE"))
        assertEquals("CCCA", KopisGenreMapper.toShcate("CLASSIC"))
        assertEquals("CCCC", KopisGenreMapper.toShcate("KOREAN_TRADITIONAL_MUSIC"))
        assertEquals("CCCD", KopisGenreMapper.toShcate("POPULAR_MUSIC"))
        assertEquals("EEEA", KopisGenreMapper.toShcate("MIXED_GENRE"))
        assertEquals("EEEB", KopisGenreMapper.toShcate("CIRCUS_AND_MAGIC"))
        assertEquals("GGGA", KopisGenreMapper.toShcate("MUSICAL"))
    }

    @Test
    fun `toShcate returns null for ALL, KID, blank and unknown codes`() {
        assertNull(KopisGenreMapper.toShcate("ALL"))
        assertNull(KopisGenreMapper.toShcate("KID"))
        assertNull(KopisGenreMapper.toShcate(""))
        assertNull(KopisGenreMapper.toShcate(null))
        assertNull(KopisGenreMapper.toShcate("NOT_A_REAL_GENRE"))
    }

    @Test
    fun `toCatecode maps each app genre to its KOPIS catecode`() {
        assertEquals("AAAA", KopisGenreMapper.toCatecode("THEATER"))
        assertEquals("BBBC", KopisGenreMapper.toCatecode("DANCE"))
        assertEquals("CCCA", KopisGenreMapper.toCatecode("CLASSIC"))
        assertEquals("CCCC", KopisGenreMapper.toCatecode("KOREAN_TRADITIONAL_MUSIC"))
        assertEquals("CCCD", KopisGenreMapper.toCatecode("POPULAR_MUSIC"))
        assertEquals("EEEA", KopisGenreMapper.toCatecode("MIXED_GENRE"))
        assertEquals("EEEB", KopisGenreMapper.toCatecode("CIRCUS_AND_MAGIC"))
        assertEquals("GGGA", KopisGenreMapper.toCatecode("MUSICAL"))
        assertEquals("KID", KopisGenreMapper.toCatecode("KID"))
    }

    @Test
    fun `toCatecode returns null for ALL, blank and unknown codes`() {
        assertNull(KopisGenreMapper.toCatecode("ALL"))
        assertNull(KopisGenreMapper.toCatecode(""))
        assertNull(KopisGenreMapper.toCatecode(null))
        assertNull(KopisGenreMapper.toCatecode("NOT_A_REAL_GENRE"))
    }
}
