package com.bangkit2024.tourid.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.bangkit2024.tourid.data.remote.response.DetailResponse
import com.bangkit2024.tourid.data.remote.response.TourResponseItem
import com.bangkit2024.tourid.data.remote.retrofit.ApiConfig
import com.bangkit2024.tourid.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TourRepository(
    private val apiService: ApiService
//    private val tourDao: DaoTourism,
//    private val db: DatabaseTourism
) {
    private val _detail = MutableLiveData<DetailResponse>()
    val detail: LiveData<DetailResponse> get() = _detail
    fun fetchDetail(id: String) {
        ApiConfig.getApiService().getDetail(id).enqueue(object : Callback<DetailResponse> {
            override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
                if (response.isSuccessful) {
                    _detail.value = response.body()
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }
        })
    }
    suspend fun getHomeList(): List<TourResponseItem> {
        return withContext(Dispatchers.IO) {
            val response = apiService.tourHome()
            response
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

//    fun getBookmarkedTourism(): LiveData<List<EntityTourism>> {
//        return tourDao.getBookmarkedTourism()
//    }

//    suspend fun setTourBookmark(news: EntityTourism, bookmarkState: Boolean) {
//        news.isBookmarked = bookmarkState
//        tourDao.updateTourism(news)
//    }

    companion object {
        @Volatile
        private var instance: TourRepository? = null
        fun getInstance(
            apiService: ApiService
//            tourDao: DaoTourism,
//            db: DatabaseTourism
        ): TourRepository =
            instance ?: synchronized(this) {
                instance ?: TourRepository(apiService)
            }.also { instance = it }
    }
}