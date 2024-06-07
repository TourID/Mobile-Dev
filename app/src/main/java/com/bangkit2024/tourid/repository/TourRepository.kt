package com.bangkit2024.tourid.repository

import com.bangkit2024.tourid.data.ApiService
import com.bangkit2024.tourid.data.GithubUsersResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TourRepository private constructor(
    private val apiService: ApiService
) {

    suspend fun getUserList(): List<GithubUsersResponseItem> {
        return withContext(Dispatchers.IO) {
            val response = apiService.userList()
            response
        }
    }

    suspend fun searchUser(query: String): List<GithubUsersResponseItem> {
        val response = apiService.searchUser(query)
        return response.items
    }

    companion object {
        @Volatile
        private var instance: TourRepository? = null
        fun getInstance(
            apiService: ApiService
        ): TourRepository =
            instance ?: synchronized(this) {
                instance ?: TourRepository(apiService)
            }.also { instance = it }
    }
}