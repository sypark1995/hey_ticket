package com.sypark.domain.usecase

import com.sypark.domain.repository.TicketRepository
import javax.inject.Inject

class GetPerformanceListUseCase @Inject constructor(
    private val repository: TicketRepository
) {
    suspend operator fun invoke(genreCode: String?, page: Int, rows: Int) =
        repository.getPerformanceList(genreCode, page, rows)
}
