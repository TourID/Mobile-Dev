package com.bangkit2024.tourid.data.remote.retrofit

import com.bangkit2024.tourid.data.remote.response.DetailResponse
import com.bangkit2024.tourid.data.remote.response.Recommendation
import com.bangkit2024.tourid.data.remote.response.RequestBookmark
import com.bangkit2024.tourid.data.remote.response.ReviewsItem
import com.bangkit2024.tourid.data.remote.response.TourResponseItem
import com.bangkit2024.tourid.data.remote.response.UserRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("tourism/{category}")
    suspend fun listTour(
        @Path("category") category: String
    ): List<TourResponseItem>

    @GET("search")
    suspend fun search(
        @Query("q") q: String
    ): List<TourResponseItem>

    @GET("detail-tourism/{placeId}")
    suspend fun detailItem(
        @Path("placeId") placeId: Int
    ): DetailResponse

    @POST("add-review")
    suspend fun addReview(@Body reviewItem: ReviewsItem)

    @Headers("Content-Type: application/json")
    @POST("recommend")
    suspend fun getRecommendations(@Body userRequest: UserRequest): List<Recommendation>

    @GET("{userId}")
    suspend fun getBookmarkUser(
        @Path("userId") userId: String
    ): List<TourResponseItem>

    @POST("add-bookmark")
    suspend fun addBookmarkUser(
        @Body requestBookmark: RequestBookmark
    )

    @DELETE("delete-bookmark")
    suspend fun deleteBookmark(
        @Body deleteRequest: RequestBookmark
    )
}