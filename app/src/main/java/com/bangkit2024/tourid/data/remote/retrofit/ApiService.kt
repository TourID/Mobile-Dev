package com.bangkit2024.tourid.data.remote.retrofit

import com.bangkit2024.tourid.data.remote.response.TourResponseAllItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("tourism/{category}")
    suspend fun listTour(
        @Path("category") category: String
    ): List<TourResponseAllItem>

    @GET("search")
    suspend fun search(
        @Query("q") q: String
    ): List<TourResponseAllItem>

}