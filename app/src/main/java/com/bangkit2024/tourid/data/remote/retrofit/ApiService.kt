package com.bangkit2024.tourid.data.remote.retrofit

import com.bangkit2024.tourid.data.remote.response.GithubSearchResponse
import com.bangkit2024.tourid.data.remote.response.GithubUsersResponseItem
import com.bangkit2024.tourid.data.remote.response.ResponseNews
import com.bangkit2024.tourid.data.remote.response.TourResponseAllItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun userList(): List<GithubUsersResponseItem>

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") q: String
    ): GithubSearchResponse

    @GET("top-headlines?country=id")
    suspend fun listNews(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): ResponseNews

    @GET("top-headlines?country=id")
    suspend fun listNewsHome(
        @Query("apiKey") apiKey: String
    ): ResponseNews

    @GET("tourism/All")
    suspend fun listTour(): List<TourResponseAllItem>

}