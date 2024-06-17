package com.bangkit2024.tourid.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.tourid.data.remote.response.TourResponseItem
import com.bangkit2024.tourid.repository.TourRepository
import com.bangkit2024.tourid.utils.Event
import kotlinx.coroutines.launch

class SearchViewModel(private val repo: TourRepository) : ViewModel() {

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _searchResult = MutableLiveData<List<TourResponseItem>>()
    val searchResult : LiveData<List<TourResponseItem>> = _searchResult

    fun getTourism(category: String) = repo.getListTour(category)

    //
    fun getSearchResult(q: String) = viewModelScope.launch {
        _isLoading.value = true
        try {
            val result = repo.searchTour(q)
            if (result.isEmpty()) {
                showToast("No search results")
                _searchResult.value = emptyList()
            } else {
                showToast(q)
                _searchResult.value = result
            }
        } catch (e: Exception) {
            Log.d("SearchViewModel", "Problem : ${e.message}")
            showToast("Can't search: ${e.message}")
        } finally {
            _isLoading.value = false
        }
    }

//    fun saveTour(tour: EntityTourism) = viewModelScope.launch {
//        repo.setTourBookmark(tour, true)
//        showToast("Get Bookmark")
//    }
//
//    fun deleteTour(tour: EntityTourism) = viewModelScope.launch {
//        repo.setTourBookmark(tour, false)
//        showToast("Delete Bookmark")
//    }

    private fun showToast(msg: String) {
        _toastText.value = Event(msg)
    }
}