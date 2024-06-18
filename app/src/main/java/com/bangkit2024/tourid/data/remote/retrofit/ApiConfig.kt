package com.bangkit2024.tourid.data.remote.retrofit

import com.bangkit2024.tourid.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {

    companion object {

        // Retrofit instance for BASE_URL_TOUR
        private var retrofitTour: Retrofit? = null

        // Retrofit instance for BASE_URL_WEATHER
        private var retrofitWeather: Retrofit? = null

        fun getApiServiceTour(): ApiService {
            if (retrofitTour == null) {
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                }

                val okhttp = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build()

                retrofitTour = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okhttp)
                    .build()
            }

            return retrofitTour!!.create(ApiService::class.java)
        }

        fun getApiServiceWeather(): ApiWeatherService {
            if (retrofitWeather == null) {
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                }

                val okhttp = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build()

                retrofitWeather = Retrofit.Builder()
                    .baseUrl(BuildConfig.WEATHER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okhttp)
                    .build()
            }

            return retrofitWeather!!.create(ApiWeatherService::class.java)
        }
    }
}