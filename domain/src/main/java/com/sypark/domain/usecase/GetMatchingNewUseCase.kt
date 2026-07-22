package com.sypark.domain.usecase

import com.sypark.domain.repository.TicketRepository
import javax.inject.Inject

class GetMatchingNewUseCase @Inject constructor(
    private val repository: TicketRepository
) {
    suspend operator fun invoke(genreCode: String?, areaCode: String?, rows: Int) =
        repository.getMatchingNew(genreCode, areaCode, rows)
}
