package com.sypark.data.di

import com.sypark.data.repository.*
import com.sypark.domain.repository.CategoryRepository
import com.sypark.domain.repository.TicketDetailRepository
import com.sypark.domain.repository.TicketRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsTicketRepository(
        ticketRepositoryImpl: TicketRepositoryImpl
    ): TicketRepository

    @Binds
    fun bindsSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    fun bindsTicketDetailRepository(
        ticketDetailRepositoryImpl: TicketDetailRepositoryImpl
    ): TicketDetailRepository

    @Binds
    fun bindsCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository
}