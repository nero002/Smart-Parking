package com.nero.bookparking.di

import com.nero.bookparking.repository.ParkingDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun providesParkingDataRepository(): ParkingDataRepository {
        return ParkingDataRepository()
    }

}