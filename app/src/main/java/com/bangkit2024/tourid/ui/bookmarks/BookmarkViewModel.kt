package com.bangkit2024.tourid.ui.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.tourid.Event
import com.bangkit2024.tourid.data.local.entity.EntityTour
import com.bangkit2024.tourid.repository.TourRepository
import kotlinx.coroutines.launch

class BookmarkViewModel(private val repo: TourRepository) : ViewModel() {

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    fun getBookmarkedNews() = repo.getBookmarkedNews()

    fun saveNews(news: EntityTour) {
        viewModelScope.launch {
            repo.setNewsBookmark(news, true)
        }
        showToast("Get Bookmark")
    }

    fun deleteNews(news: EntityTour) {
        viewModelScope.launch {
            repo.setNewsBookmark(news, false)
        }
        showToast("Delete Bookmark")
    }

    private fun showToast(msg: String) {
        _toastText.value = Event(msg)
    }
}