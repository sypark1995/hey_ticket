package com.sypark.domain.usecase

import com.sypark.domain.repository.TicketRepository
import javax.inject.Inject

class GetPerformanceNewUseCase @Inject constructor(
    private val repository: TicketRepository
) {
    suspend operator fun invoke(genreCode: String?, page: Int, rows: Int) =
        repository.getNew(genreCode, page, rows)
}
