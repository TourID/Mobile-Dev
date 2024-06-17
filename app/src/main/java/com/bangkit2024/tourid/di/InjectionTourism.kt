package com.bangkit2024.tourid.di

import android.content.Context
import com.bangkit2024.tourid.data.remote.retrofit.ApiConfig
import com.bangkit2024.tourid.data.remote.retrofit.ApiService
import com.bangkit2024.tourid.repository.TourRepository

object InjectionTourism {
    private fun provideApiServiceTour(): ApiService {
        return ApiConfig.getApiService()
    }

    private fun provideRepositoryTour(context: Context): TourRepository {
        val apiService = provideApiServiceTour()
        return TourRepository.getInstance(apiService)
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val repository = provideRepositoryTour(context)
        return ViewModelFactory(repository)
    }
}