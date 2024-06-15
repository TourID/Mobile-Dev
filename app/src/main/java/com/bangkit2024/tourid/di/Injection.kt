package com.bangkit2024.tourid.di

import android.content.Context
import com.bangkit2024.tourid.data.local.room.DatabaseTourism
import com.bangkit2024.tourid.data.remote.retrofit.ApiConfig
import com.bangkit2024.tourid.data.remote.retrofit.ApiService
import com.bangkit2024.tourid.repository.TourRepository

//object Injection {
//    private fun provideApiService(): ApiService {
//        return ApiConfig.getApiService()
//    }
//
//    private fun provideRepository(context: Context): TourRepository {
//        val apiService = provideApiService()
//        val database = DatabaseTour.getInstance(context)
//        val dao = database.bookmarkDao()
//        return TourRepository.getInstance(apiService, dao)
//    }
//
//    fun provideViewModelFactory(context: Context): ViewModelFactory {
//        val repository = provideRepository(context)
//        return ViewModelFactory(repository)
//    }
//}


object InjectionTourism {
    private fun provideApiServiceTour(): ApiService {
        return ApiConfig.getApiService()
    }

    private fun provideRepositoryTour(context: Context): TourRepository {
        val apiService = provideApiServiceTour()
        val database = DatabaseTourism.getInstance(context)
        val dao = database.bookmarkDao()
        return TourRepository.getInstance(apiService, dao)
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val repository = provideRepositoryTour(context)
        return ViewModelFactory(repository)
    }
}