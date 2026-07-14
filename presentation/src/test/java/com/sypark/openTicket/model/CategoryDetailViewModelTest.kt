package com.sypark.openTicket.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sypark.data.paging.PagingRepository
import com.sypark.data.service.KopisApiService
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class CategoryDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private fun newViewModel(): CategoryDetailViewModel {
        val fakeService = object : KopisApiService {
            override suspend fun requestPerformanceList(serviceKey: String, startDate: String, endDate: String, page: Int, rows: Int, genreCode: String?, areaCode: String?, prfstate: String?) = ""
            override suspend fun requestPerformanceSearch(serviceKey: String, startDate: String, endDate: String, page: Int, rows: Int, title: String) = ""
            override suspend fun requestPerformanceDetail(performanceId: String, serviceKey: String) = ""
            override suspend fun requestFacilityDetail(venueId: String, serviceKey: String) = ""
        }
        return CategoryDetailViewModel(PagingRepository(fakeService))
    }

    @Test
    fun `selecting a status turns off the other two`() {
        val viewModel = newViewModel()

        viewModel.isChecked(CategoryDetailViewModel.Status.ONGOING)

        assertTrue(viewModel.isPlaned.value!!)
        assertFalse(viewModel.isDuring.value!!)
        assertFalse(viewModel.isFinished.value!!)
        assertEquals(listOf(CategoryDetailViewModel.Status.ONGOING), viewModel.statusList.value)

        viewModel.isChecked(CategoryDetailViewModel.Status.COMPLETED)

        assertFalse(viewModel.isPlaned.value!!)
        assertFalse(viewModel.isDuring.value!!)
        assertTrue(viewModel.isFinished.value!!)
        assertEquals(listOf(CategoryDetailViewModel.Status.COMPLETED), viewModel.statusList.value)
    }

    @Test
    fun `tapping the already-selected status turns it back off`() {
        val viewModel = newViewModel()
        viewModel.isChecked(CategoryDetailViewModel.Status.UPCOMING)

        viewModel.isChecked(CategoryDetailViewModel.Status.UPCOMING)

        assertFalse(viewModel.isPlaned.value!!)
        assertFalse(viewModel.isDuring.value!!)
        assertFalse(viewModel.isFinished.value!!)
        assertTrue(viewModel.statusList.value.isNullOrEmpty())
    }
}
