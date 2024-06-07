package com.bangkit2024.tourid.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun userList(): List<GithubUsersResponseItem>

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") q: String
    ): GithubSearchResponse
}