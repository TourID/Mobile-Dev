package com.bangkit2024.tourid.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.tourid.data.remote.response.DetailResponse
import com.bangkit2024.tourid.data.remote.response.RequestBookmark
import com.bangkit2024.tourid.data.remote.response.ReviewsItem
import com.bangkit2024.tourid.data.remote.response.TourResponseItem
import com.bangkit2024.tourid.repository.TourRepository
import com.bangkit2024.tourid.utils.Event
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


class DetailViewModel(private val repo: TourRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    private val _detail = MutableLiveData<DetailResponse>()
    val detail: LiveData<DetailResponse> get() = _detail

    private val _reviewAdded = MutableLiveData<Boolean>()
    val reviewAdded: LiveData<Boolean> = _reviewAdded

    private val _bookmarks = MutableLiveData<List<TourResponseItem>>()
    val bookmarks: LiveData<List<TourResponseItem>> get() = _bookmarks

    private val _bookmarkAdded = MutableLiveData<Boolean>()
    val bookmarkAdded: LiveData<Boolean> get() = _bookmarkAdded

    private val _bookmarkRemoved = MutableLiveData<Boolean>()
    val bookmarkRemoved: LiveData<Boolean> get() = _bookmarkRemoved

    fun fetchDetail(id: Int) = viewModelScope.launch {
        _isLoading.value = true
        try {
            _detail.value = repo.fetchDetailItem(id)
        } catch (e: Exception) {
            showToast("Cant fetch detail : ${e.message}")
        }
        _isLoading.value = false
    }

    fun addReview(review: String, rating: Double, placeId: Int) {
        viewModelScope.launch {
            try {
                val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                val username = FirebaseAuth.getInstance().currentUser?.displayName ?: ""

                val reviewItem = ReviewsItem(
                    review = review,
                    placeId = placeId,
                    rating = rating.toInt(),
                    userId = userId,
                    username = username
                )

                repo.addReview(reviewItem)
                _reviewAdded.value = true
            } catch (e: Exception) {
                Log.e("Detail View Model", "Failed to add review", e)
                _reviewAdded.value = false
            }
        }
    }

    fun getBookmarks(userId: String) = viewModelScope.launch {
        _isLoading.value = true
        try {
            _bookmarks.value = repo.getBookmarks(userId)
        } catch (e: Exception) {
            showToast("Failed to fetch bookmarks: ${e.message}")
        }
        _isLoading.value = false
    }

    fun addBookmark(placeId: Int) = viewModelScope.launch {
        try {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            repo.addBookmark(RequestBookmark(userId, placeId))
            _bookmarkAdded.value = true
        } catch (e: Exception) {
            showToast("Failed to add bookmark: ${e.message}")
            _bookmarkAdded.value = false
        }
    }

    fun deleteBookmark(placeId: Int) = viewModelScope.launch {
        try {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            repo.deleteBookmark(RequestBookmark(userId, placeId))
            _bookmarkRemoved.value = true
        } catch (e: Exception) {
            showToast("Failed to remove bookmark: ${e.message}")
            _bookmarkRemoved.value = false
        }
    }
    private fun showToast(msg: String){
        _toastText.value = Event(msg)
    }
}