package com.bangkit2024.tourid.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit2024.tourid.data.ApiService
import com.bangkit2024.tourid.data.DummyResponse
import com.bangkit2024.tourid.data.ProductsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TourRepository private constructor(
    private val apiService: ApiService
) {

    suspend fun getItemList(): List<ProductsItem> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getProducts()
            response
        }
    }

    suspend fun searchUser(query: String): List<ProductsItem> {
        val response = apiService.getResultProducts(query)
        return response.products
    }

//    suspend fun getLocation(): ListStoryResponse {
//        val token = getSession().first().token
//        return withContext(Dispatchers.IO) {
//            val response = ApiConfig.getApiService(token).getLocationStory()
//            response
//        }
//    }

//    fun uploadPostStory(image: File, desc: String) = liveData {
//        val token = getSession().first().token
//        emit(ResultState.Loading)
//        val requestBody = desc.toRequestBody("text/plain".toMediaType())
//        val requestImageFile = image.asRequestBody("image/jpeg".toMediaType())
//        val multiPartBody = MultipartBody.Part.createFormData(
//            "photo",
//            image.name,
//            requestImageFile
//        )
//        try {
//            val successResponse =
//                ApiConfig.getApiService(token).addStory(multiPartBody, requestBody)
//            emit(ResultState.Success(successResponse))
//        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
//            emit(errorResponse.message?.let { ResultState.Error(it) })
//        }
//    }

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