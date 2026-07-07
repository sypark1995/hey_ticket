package com.sypark.domain.usecase

import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.GenreCount
import com.sypark.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetGenreCountUseCaseTest {

    @Test
    fun `invoke delegates to repository with given genre codes`() = runTest {
        val repository = object : CategoryRepository {
            override suspend fun getGenreCountList(genreCodes: List<String>) =
                flowOf(ApiResult.Success(listOf(GenreCount("THEATER", 3))))
        }
        val useCase = GetGenreCountUseCase(repository)

        useCase(listOf("THEATER")).collect { result ->
            assertEquals(ApiResult.Success(listOf(GenreCount("THEATER", 3))), result)
        }
    }
}
