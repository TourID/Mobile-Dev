package com.bangkit2024.tourid.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.bangkit2024.tourid.data.local.entity.EntityTourism
import com.bangkit2024.tourid.data.local.room.DaoTourism
import com.bangkit2024.tourid.data.remote.retrofit.ApiService

//class TourRepository private constructor(
//    private val apiService: ApiService,
//    private val newsDao: DaoTour
//) {
//
//    fun getHeadlineNews(category: String): LiveData<Result<List<EntityTour>>> = liveData {
//        emit(Result.Loading)
//        try {
//            val response = apiService.listNews(category, BuildConfig.API_KEY)
//            val articles = response.articles
//            val newsList = articles.map { article ->
//                val isBookmarked = newsDao.isNewsBookmarked(article.title)
//                EntityTour(
//                    article.title,
//                    article.publishedAt,
//                    article.urlToImage,
//                    article.url,
//                    isBookmarked
//                )
//            }
//            newsDao.deleteAll()
//            newsDao.insertNews(newsList)
//        } catch (e: Exception) {
//            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
//            emit(Result.Error(e.message.toString()))
//        }
//        val localData: LiveData<Result<List<EntityTour>>> = newsDao.getNews().map { Result.Success(it) }
//        emitSource(localData)
//    }
//
//    fun getHeadlineNewsHome(): LiveData<Result<List<EntityTour>>> = liveData {
//        emit(Result.Loading)
//        try {
//            val response = apiService.listNewsHome(BuildConfig.API_KEY)
//            val articles = response.articles
//            val newsList = articles.map { article ->
//                val isBookmarked = newsDao.isNewsBookmarked(article.title)
//                EntityTour(
//                    article.title,
//                    article.publishedAt,
//                    article.urlToImage,
//                    article.url,
//                    isBookmarked
//                )
//            }
//            newsDao.deleteAll()
//            newsDao.insertNews(newsList)
//        } catch (e: Exception) {
//            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
//            emit(Result.Error(e.message.toString()))
//        }
//        val localData: LiveData<Result<List<EntityTour>>> = newsDao.getNews().map { Result.Success(it) }
//        emitSource(localData)
//    }
//
//    fun getBookmarkedNews(): LiveData<List<EntityTour>> {
//        return newsDao.getBookmarkedNews()
//    }
//
//    suspend fun setNewsBookmark(news: EntityTour, bookmarkState: Boolean) {
//        news.isBookmarked = bookmarkState
//        newsDao.updateNews(news)
//    }
//
//    companion object {
//        @Volatile
//        private var instance: TourRepository? = null
//        fun getInstance(
//            apiService: ApiService,
//            newsDao: DaoTour
//        ): TourRepository =
//            instance ?: synchronized(this) {
//                instance ?: TourRepository(apiService, newsDao)
//            }.also { instance = it }
//    }
//}


class TourRepository private constructor(
    private val apiService: ApiService,
    private val tourDao: DaoTourism
) {
    fun getListTour(): LiveData<Result<List<EntityTourism>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.listTour()
            val destinations = response
            val tourList = destinations.map { destination ->
                val isBookmarked = tourDao.isNewsTourism(destination.placeName)
                EntityTourism(
                    destination.placeId,
                    destination.category,
                    destination.imageURL,
                    destination.placeName,
                    destination.rating,
                    destination.city,
                    isBookmarked
                )
            }
            tourDao.deleteAll()
            tourDao.insertTourism(tourList)
        } catch (e: Exception) {
            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<EntityTourism>>> = tourDao.getTourism().map { Result.Success(it) }
        emitSource(localData)
    }

    fun getBookmarkedTourism(): LiveData<List<EntityTourism>> {
        return tourDao.getBookmarkedTourism()
    }

    suspend fun setTourBookmark(news: EntityTourism, bookmarkState: Boolean) {
        news.isBookmarked = bookmarkState
        tourDao.updateTourism(news)
    }

    companion object {
        @Volatile
        private var instance: TourRepository? = null
        fun getInstance(
            apiService: ApiService,
            tourDao: DaoTourism
        ): TourRepository =
            instance ?: synchronized(this) {
                instance ?: TourRepository(apiService, tourDao)
            }.also { instance = it }
    }
}