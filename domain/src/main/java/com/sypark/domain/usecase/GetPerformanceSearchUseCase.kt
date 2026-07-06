package com.sypark.domain.usecase

import com.sypark.domain.repository.TicketRepository
import javax.inject.Inject

class GetPerformanceSearchUseCase @Inject constructor(
    private val repository: TicketRepository
) {
    suspend operator fun invoke(query: String, page: Int, rows: Int) =
        repository.search(query, page, rows)
}
