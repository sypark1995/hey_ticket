package com.sypark.openTicket.model

import com.sypark.domain.model.Request
import com.sypark.domain.repository.OpenTicketRepository
import com.sypark.domain.usecase.GetInterParkListUseCase
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val openTicketRepository : OpenTicketRepository
): BaseViewModel() {


}