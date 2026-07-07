package com.sypark.domain.usecase

import com.sypark.domain.repository.CategoryRepository
import javax.inject.Inject

class GetGenreCountUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(genreCodes: List<String>) = repository.getGenreCountList(genreCodes)
}
