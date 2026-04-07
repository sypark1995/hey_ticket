package com.sypark.data.repository

import com.sypark.data.db.entity.BaseResponse
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getGenreCountList(): Flow<BaseResponse>
}