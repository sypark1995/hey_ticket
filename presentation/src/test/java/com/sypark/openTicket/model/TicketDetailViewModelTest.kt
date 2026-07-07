package com.sypark.openTicket.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sypark.domain.model.ApiResult
import com.sypark.domain.model.TicketDetail
import com.sypark.domain.repository.TicketDetailRepository
import com.sypark.domain.usecase.GetTicketDetailUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class TicketDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val fakeDetail = TicketDetail(
        id = "PF223939", title = "위키드", startDate = "2026.07.01", endDate = "2026.09.30",
        theater = "블루스퀘어", cast = "", crew = "", runtime = "", age = "", company = "",
        price = "R석 160,000원 S석 130,000원", poster = "", story = "", genre = "뮤지컬",
        status = "공연중", openRun = false, storyUrls = emptyList(), schedule = "", views = 0,
        latitude = 37.53987, longitude = 126.99931, address = "서울특별시 용산구 이태원로 294",
        phoneNumber = "02-1552-1369",
    )

    @Test
    fun `getTicketDetailData exposes detail from use case`() = runTest {
        val repository = object : TicketDetailRepository {
            override suspend fun getTicketDetail(id: String) = flowOf(ApiResult.Success(fakeDetail))
        }
        val viewModel = TicketDetailViewModel(GetTicketDetailUseCase(repository))

        viewModel.getTicketDetailData("PF223939")

        assertEquals(fakeDetail, viewModel.ticketDetail.value)
        assertEquals(true, viewModel.loading.value)
    }
}
