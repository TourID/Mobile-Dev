package com.bangkit2024.tourid.di

import com.bangkit2024.tourid.data.ApiConfig
import com.bangkit2024.tourid.repository.TourRepository

object Injection {
    fun provideRepo(): TourRepository {
        val apiService = ApiConfig.getApiService()
        return TourRepository.getInstance(apiService)
    }
}