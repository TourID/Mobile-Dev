package com.bangkit2024.tourid.data.remote.retrofit

import com.bangkit2024.tourid.data.remote.response.GithubSearchResponse
import com.bangkit2024.tourid.data.remote.response.GithubUsersResponseItem
import com.bangkit2024.tourid.data.remote.response.ResponseNews
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun userList(): List<GithubUsersResponseItem>

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") q: String
    ): GithubSearchResponse

    @GET("top-headlines?country=id&category=sports")
    suspend fun listNews(@Query("apiKey") apiKey: String): ResponseNews
}