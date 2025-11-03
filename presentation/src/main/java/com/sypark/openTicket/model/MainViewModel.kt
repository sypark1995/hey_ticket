package com.sypark.openTicket.model

import com.sypark.domain.repository.MainRepository
import com.sypark.openTicket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository : MainRepository
): BaseViewModel() {


}