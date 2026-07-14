package com.sypark.data.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KopisApiService {

    @GET("pblprfr")
    suspend fun requestPerformanceList(
        @Query("service") serviceKey: String,
        @Query("stdate") startDate: String,
        @Query("eddate") endDate: String,
        @Query("cpage") page: Int,
        @Query("rows") rows: Int,
        @Query("shcate") genreCode: String? = null,
        @Query("signgucode") areaCode: String? = null,
        @Query("prfstate") prfstate: String? = null,
    ): String

    @GET("pblprfr")
    suspend fun requestPerformanceSearch(
        @Query("service") serviceKey: String,
        @Query("stdate") startDate: String,
        @Query("eddate") endDate: String,
        @Query("cpage") page: Int,
        @Query("rows") rows: Int,
        @Query("shprfnm") title: String,
    ): String

    @GET("pblprfr/{id}")
    suspend fun requestPerformanceDetail(
        @Path("id") performanceId: String,
        @Query("service") serviceKey: String,
    ): String

    @GET("prfplc/{id}")
    suspend fun requestFacilityDetail(
        @Path("id") venueId: String,
        @Query("service") serviceKey: String,
    ): String
}
