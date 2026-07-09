package com.sypark.openTicket.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.GenreCount
import com.sypark.domain.repository.CategoryRepository
import com.sypark.domain.usecase.GetGenreCountUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test

class CategoryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `getGenreCountList excludes KID from the request and hides zero-count genres`() = runTest {
        var requestedCodes: List<String> = emptyList()
        val repository = object : CategoryRepository {
            override suspend fun getGenreCountList(genreCodes: List<String>): Flow<ApiResult<List<GenreCount>>> {
                requestedCodes = genreCodes
                return flowOf(
                    ApiResult.Success(
                        listOf(
                            GenreCount("ALL", 10),
                            GenreCount("THEATER", 0),
                            GenreCount("MUSICAL", 3),
                        )
                    )
                )
            }
        }
        val viewModel = CategoryViewModel(GetGenreCountUseCase(repository))

        viewModel.getGenreCountList()

        assertFalse(requestedCodes.contains("KID"))
        assertEquals(listOf("ALL", "MUSICAL"), viewModel.genreCountList.value?.map { it.genre })
    }
}
