package com.bangkit2024.tourid.ui.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit2024.tourid.utils.Event
import com.bangkit2024.tourid.repository.TourRepository

class BookmarkViewModel(private val repo: TourRepository) : ViewModel() {

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
//    fun getBookmarkedTour() = repo.getBookmarkedTourism()
//
//    fun saveTour(tour: EntityTourism) {
//        viewModelScope.launch {
//            repo.setTourBookmark(tour, true)
//        }
//        showToast("Get Bookmark")
//    }
//
//    fun deleteTour(tour: EntityTourism) {
//        viewModelScope.launch {
//            repo.setTourBookmark(tour, false)
//        }
//        showToast("Delete Bookmark")
//    }

    private fun showToast(msg: String) {
        _toastText.value = Event(msg)
    }
}