package com.example.interviewdemo.di

import com.example.interviewdemo.repositories.WorkshopDetailsRepository
import com.example.interviewdemo.repositories.WorkshopDetailsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun providesWorkshopDetailsRepository(workshopDetailRepositoryImpl: WorkshopDetailsRepositoryImpl): WorkshopDetailsRepository

}