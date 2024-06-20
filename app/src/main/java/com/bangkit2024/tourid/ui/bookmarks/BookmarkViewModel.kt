package com.bangkit2024.tourid.ui.bookmarks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.tourid.data.remote.response.RequestBookmark
import com.bangkit2024.tourid.data.remote.response.TourResponseItem
import com.bangkit2024.tourid.repository.TourRepository
import com.bangkit2024.tourid.utils.Event
import kotlinx.coroutines.launch

class BookmarkViewModel(private val repo: TourRepository) : ViewModel() {

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _bookmarks = MutableLiveData<List<TourResponseItem>?>()
    val bookmarks: LiveData<List<TourResponseItem>?> get() = _bookmarks

    private val _bookmarkAdded = MutableLiveData<Boolean>()
    val bookmarkAdded: LiveData<Boolean> get() = _bookmarkAdded

    private val _bookmarkRemoved = MutableLiveData<Boolean>()
    val bookmarkRemoved: LiveData<Boolean> get() = _bookmarkRemoved

    fun getBookmarks(userId: String) = viewModelScope.launch {
        _isLoading.value = true
        try {
            val bookmarks = repo.getBookmarks(userId)
            if (bookmarks != null) {
                _bookmarks.value = bookmarks
            } else {
                _bookmarks.value = emptyList() // Atau kosongkan jika perlu
                showToast("No bookmarks found for this user.")
            }
        } catch (e: Exception) {
            showToast("Failed to fetch bookmarks: ${e.message}")
        }
        _isLoading.value = false
    }

    fun addBookmark(userId: String, placeId: Int) = viewModelScope.launch {
        Log.d("DetailViewModel", "Adding bookmark for placeId: $placeId")
        if (userId.isNotEmpty()) {
            try {
                repo.addBookmark(RequestBookmark(userId, placeId))
                _bookmarkAdded.value = true
            } catch (e: Exception) {
                showToast("Failed to add bookmark: ${e.message}")
                _bookmarkAdded.value = false
            }
        } else {
            showToast("User ID not available. Cannot add bookmark.")
        }
    }

    fun deleteBookmark(userId: String, placeId: Int) = viewModelScope.launch {
        if (userId.isNotEmpty()) {
            try {
                repo.deleteBookmark(RequestBookmark(userId, placeId))
                _bookmarkRemoved.value = true
            } catch (e: Exception) {
                Log.d("DetailViewModel", "Failed to remove bookmark: ${e.message}")
                showToast("Failed to remove bookmark: ${e.message}")
                _bookmarkRemoved.value = false
            }
        } else {
            showToast("User ID not available. Cannot remove bookmark.")
        }
    }

    private fun showToast(msg: String) {
        _toastText.value = Event(msg)
    }
}