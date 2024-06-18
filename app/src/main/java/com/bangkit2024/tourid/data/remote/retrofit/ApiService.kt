package com.bangkit2024.tourid.data.remote.retrofit

import com.bangkit2024.tourid.data.remote.response.DetailResponse
import com.bangkit2024.tourid.data.remote.response.ReviewsItem
import com.bangkit2024.tourid.data.remote.response.TourResponseItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("detail-tourism/{placeId}")
    suspend fun detailItem(
        @Path("placeId") placeId: Int
    ): DetailResponse

    @POST("addReview")
    suspend fun addReview(@Body reviewItem: ReviewsItem)
}