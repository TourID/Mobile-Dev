package com.bangkit2024.tourid.data.remote.retrofit

import com.bangkit2024.tourid.BuildConfig.API_KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    companion object {

        fun getApiService(): ApiService {

            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                val requestHeaders = req.newBuilder()
                    .addHeader("Authorization", "token $API_KEY")
                    .build()
                chain.proceed(requestHeaders)
            }

            val okhttp = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://tourid-api-symqq5pxfq-as.a.run.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}