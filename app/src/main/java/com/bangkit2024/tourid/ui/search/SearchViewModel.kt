package com.bangkit2024.tourid.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.tourid.Event
import com.bangkit2024.tourid.data.local.entity.EntityTourism
import com.bangkit2024.tourid.data.remote.response.TourResponseAllItem
import com.bangkit2024.tourid.repository.TourRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val repo: TourRepository) : ViewModel() {

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listTour = MutableLiveData<List<TourResponseAllItem>>()
    val listTour: LiveData<List<TourResponseAllItem>> = _listTour

//    fun getHeadlineNews(category: String) = repo.getHeadlineNews(category)
//
//    fun saveNews(news: EntityTour) {
//        viewModelScope.launch {
//            repo.setNewsBookmark(news, true)
//        }
//        showToast("Get Bookmark")
//    }
//
//    fun deleteNews(news: EntityTour) {
//        viewModelScope.launch {
//            repo.setNewsBookmark(news, false)
//        }
//        showToast("Delete Bookmark")
//    }

    fun getTourism() = repo.getListTour()

    fun saveTour(tour: EntityTourism) = viewModelScope.launch {
        repo.setTourBookmark(tour, true)
        showToast("Get Bookmark")
    }

    fun deleteTour(tour: EntityTourism) = viewModelScope.launch {
        repo.setTourBookmark(tour, false)
        showToast("Delete Bookmark")
    }

    private fun showToast(msg: String) {
        _toastText.value = Event(msg)
    }
}