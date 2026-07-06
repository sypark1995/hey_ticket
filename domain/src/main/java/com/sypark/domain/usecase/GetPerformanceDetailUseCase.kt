package com.sypark.domain.usecase

import com.sypark.domain.repository.TicketRepository
import javax.inject.Inject

class GetPerformanceDetailUseCase @Inject constructor(
    private val repository: TicketRepository
) {
    suspend operator fun invoke(performanceId: String) =
        repository.getDetail(performanceId)
}
