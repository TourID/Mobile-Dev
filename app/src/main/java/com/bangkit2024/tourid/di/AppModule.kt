package com.bangkit2024.tourid.di

//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//
//    @Provides
//    @Singleton
//    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
//        return HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//    }
//
//    @Provides
//    @Singleton
//    fun provideAuthInterceptor(): Interceptor {
//        return Interceptor { chain ->
//            val newRequest = chain.request().newBuilder()
//                .build()
//            chain.proceed(newRequest)
//        }
//    }
//
//    @Provides
//    @Singleton
//    fun provideApiService(
//        loggingInterceptor: HttpLoggingInterceptor,
//        authInterceptor: Interceptor
//    ): ApiService {
//        val client = OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
//            .addInterceptor(authInterceptor)
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .build()
//
//        return retrofit.create(ApiService::class.java)
//    }
//
//    @Provides
//    @Singleton
//    fun provideRepo(apiService: ApiService): TourRepository {
//        return TourRepository.getInstance(apiService)
//    }
//}

import android.content.Context
import com.bangkit2024.tourid.data.ApiConfig
import com.bangkit2024.tourid.data.ApiService
import com.bangkit2024.tourid.repository.TourRepository

object Injection {
    private fun provideApiService(): ApiService {
        return ApiConfig.getApiService()
    }

    private fun provideRepository(context: Context): TourRepository {
        val apiService = provideApiService()
        return TourRepository.getInstance(apiService)
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val repository = provideRepository(context)
        return ViewModelFactory(repository)
    }
}