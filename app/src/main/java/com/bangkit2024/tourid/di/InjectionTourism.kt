package com.bangkit2024.tourid.di

import android.content.Context
import com.bangkit2024.tourid.data.remote.retrofit.ApiConfig
import com.bangkit2024.tourid.data.remote.retrofit.ApiService
import com.bangkit2024.tourid.data.remote.retrofit.ApiWeatherService
import com.bangkit2024.tourid.repository.TourRepository

object InjectionTourism {
    private fun provideApiServiceTour(): ApiService {
        return ApiConfig.getApiServiceTour()
    }

    private fun provideApiServiceWeather(): ApiWeatherService {
        return ApiConfig.getApiServiceWeather()
    }

    private fun provideRepositoryTour(context: Context): TourRepository {
        val apiServiceTour = provideApiServiceTour()
        val apiServiceWeather = provideApiServiceWeather()
        return TourRepository.getInstance(apiServiceTour, apiServiceWeather)
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val repository = provideRepositoryTour(context)
        return ViewModelFactory(repository)
    }
}