package com.bangkit2024.tourid.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit2024.tourid.BuildConfig
import com.bangkit2024.tourid.data.remote.response.DetailResponse
import com.bangkit2024.tourid.data.remote.response.Recommendation
import com.bangkit2024.tourid.data.remote.response.RequestBookmark
import com.bangkit2024.tourid.data.remote.response.ReviewsItem
import com.bangkit2024.tourid.data.remote.response.TourResponseItem
import com.bangkit2024.tourid.data.remote.response.UserRequest
import com.bangkit2024.tourid.data.remote.response.WeatherResponse
import com.bangkit2024.tourid.data.remote.retrofit.ApiService
import com.bangkit2024.tourid.data.remote.retrofit.ApiWeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class TourRepository(
    private val apiService: ApiService,
    private val apiWeather: ApiWeatherService
) {

    suspend fun fetchDetailItem(id: Int): DetailResponse {
        return apiService.detailItem(id)
    }

    fun getRecommendations(userId: String): LiveData<Result<List<Recommendation>>> = liveData {
        emit(Result.Loading)
        try {
            val response = withContext(Dispatchers.IO) {
                apiService.getRecommendations(UserRequest(userId))
            }
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("TourRepository", "getRecommendations: ${e.message}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getListTour(category: String): LiveData<Result<List<TourResponseItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.listTour(category)
            val tourList = response.map { destination ->
                TourResponseItem(
                    destination.placeId,
                    destination.city,
                    destination.imageUrl,
                    destination.ratingLoc,
                    destination.category,
                    destination.placeName
                )
            }
            emit(Result.Success(tourList))
        } catch (e: Exception) {
            Log.d("TourRepository", "getListTour: ${e.message}")
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun searchTour(q: String): List<TourResponseItem> {
        return apiService.search(q)
    }

    suspend fun addReview(reviewItem: ReviewsItem) {
        try {
            apiService.addReview(reviewItem)
        } catch (e: Exception) {
            Log.e("Tour Repository", "Failed to add review", e)
            throw ReviewsRepositoryException("Failed to add review", e)
        }
    }

    suspend fun getBookmarks(userId: String): List<TourResponseItem>? {
        return try {
            if (userId.isNotEmpty()) {
                apiService.getBookmarkUser(userId)
            } else {
                throw IllegalArgumentException("User ID cannot be empty")
            }
        } catch (e: HttpException) {
            if (e.code() == 404) {
                null
            } else {
                throw e
            }
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun addBookmark(requestBookmark: RequestBookmark) {
        if (requestBookmark.userId.isNotEmpty()) {
            apiService.addBookmarkUser(requestBookmark)
        } else {
            throw IllegalArgumentException("User ID cannot be empty")
        }
    }

    suspend fun deleteBookmark(requestBookmark: RequestBookmark) {
        if (requestBookmark.userId.isNotEmpty()) {
            apiService.deleteBookmark(requestBookmark)
        } else {
            throw IllegalArgumentException("User ID cannot be empty")
        }
    }

    fun getWeatherByCoordinates(lat: Double, lon: Double, callback: (WeatherResponse?) -> Unit) {
        apiWeather.getWeatherByCoordinates(lat, lon, BuildConfig.weatherKey).enqueue(object :
            Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    companion object {
        @Volatile
        private var instance: TourRepository? = null
        fun getInstance(
            apiService: ApiService,
            apiWeather: ApiWeatherService
        ): TourRepository =
            instance ?: synchronized(this) {
                instance ?: TourRepository(apiService, apiWeather)
            }.also { instance = it }
    }
}

class ReviewsRepositoryException(message: String, cause: Throwable?) : Exception(message, cause)