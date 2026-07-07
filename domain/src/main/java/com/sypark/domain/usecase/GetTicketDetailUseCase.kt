package com.sypark.domain.usecase

import com.sypark.domain.repository.TicketDetailRepository
import javax.inject.Inject

class GetTicketDetailUseCase @Inject constructor(
    private val repository: TicketDetailRepository
) {
    suspend operator fun invoke(id: String) = repository.getTicketDetail(id)
}
