package com.bangkit2024.tourid.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.bangkit2024.tourid.BuildConfig
import com.bangkit2024.tourid.data.local.entity.EntityTour
import com.bangkit2024.tourid.data.local.room.DaoTour
import com.bangkit2024.tourid.data.remote.retrofit.ApiService

class TourRepository private constructor(
    private val apiService: ApiService,
    private val newsDao: DaoTour
) {

//    suspend fun getUserList(): List<GithubUsersResponseItem> {
//        return withContext(Dispatchers.IO) {
//            val response = apiService.userList()
//            response
//        }
//    }
//
//    suspend fun searchUser(query: String): List<GithubUsersResponseItem> {
//        val response = apiService.searchUser(query)
//        return response.items
//    }

    fun getHeadlineNews(): LiveData<Result<List<EntityTour>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.listNews(BuildConfig.API_KEY)
            val articles = response.articles
            val newsList = articles.map { article ->
                val isBookmarked = newsDao.isNewsBookmarked(article.title)
                EntityTour(
                    article.title,
                    article.publishedAt,
                    article.urlToImage,
                    article.url,
                    isBookmarked
                )
            }
            newsDao.deleteAll()
            newsDao.insertNews(newsList)
        } catch (e: Exception) {
            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<EntityTour>>> = newsDao.getNews().map { Result.Success(it) }
        emitSource(localData)
    }

    fun getBookmarkedNews(): LiveData<List<EntityTour>> {
        return newsDao.getBookmarkedNews()
    }

    suspend fun setNewsBookmark(news: EntityTour, bookmarkState: Boolean) {
        news.isBookmarked = bookmarkState
        newsDao.updateNews(news)
    }

    companion object {
        @Volatile
        private var instance: TourRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: DaoTour
        ): TourRepository =
            instance ?: synchronized(this) {
                instance ?: TourRepository(apiService, newsDao)
            }.also { instance = it }
    }
}