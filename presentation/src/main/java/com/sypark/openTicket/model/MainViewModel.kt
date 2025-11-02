package com.sypark.openTicket.model

import com.sypark.domain.model.Request
import com.sypark.domain.usecase.GetInterParkListUseCase
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class MainViewModel(private val getInterParkListUseCase: GetInterParkListUseCase): BaseViewModel() {

    suspend fun getMelonData(request: Request) {
        getInterParkListUseCase(request)
    }
}