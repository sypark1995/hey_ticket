package com.sypark.domain.usecase

import com.sypark.domain.repository.TicketRepository
import javax.inject.Inject

class GetClosingSoonUseCase @Inject constructor(
    private val repository: TicketRepository
) {
    suspend operator fun invoke(rows: Int) =
        repository.getClosingSoon(rows)
}
