package com.sypark.data.di

import com.sypark.data.repository.OpenTicketRepositoryImpl
import com.sypark.domain.repository.OpenTicketRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsOpenTicketRepository(
        openTicketRepositoryImpl: OpenTicketRepositoryImpl
    ): OpenTicketRepository


}