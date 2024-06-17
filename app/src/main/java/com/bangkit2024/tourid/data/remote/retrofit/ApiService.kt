package com.bangkit2024.tourid.data.remote.retrofit

import com.bangkit2024.tourid.data.remote.response.TourResponseItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("tourism/{category}")
    suspend fun listTour(
        @Path("category") category: String
    ): List<TourResponseItem>

    @GET("tourism/All")
    suspend fun tourHome(): List<TourResponseItem>

    @GET("search")
    suspend fun search(
        @Query("q") q: String
    ): List<TourResponseItem>

}