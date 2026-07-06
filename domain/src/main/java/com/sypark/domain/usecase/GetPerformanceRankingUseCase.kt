package com.sypark.domain.usecase

import com.sypark.domain.repository.TicketRepository
import javax.inject.Inject

class GetPerformanceRankingUseCase @Inject constructor(
    private val repository: TicketRepository
) {
    suspend operator fun invoke(periodType: String, genreCode: String?, areaCode: String?) =
        repository.getRanking(periodType, genreCode, areaCode)
}
