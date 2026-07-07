package com.sypark.domain.repository

import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.GenreCount
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getGenreCountList(genreCodes: List<String>): Flow<ApiResult<List<GenreCount>>>
}
