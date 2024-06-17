package com.bangkit2024.tourid.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.tourid.data.remote.response.TourResponseItem
import com.bangkit2024.tourid.repository.TourRepository
import com.bangkit2024.tourid.utils.Event
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: TourRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    private val _homeTourList = MutableLiveData<List<TourResponseItem>>()
    val homeTourList: LiveData<List<TourResponseItem>> = _homeTourList

//    fun saveTour(tour: EntityTourism) = viewModelScope.launch {
//        repo.setTourBookmark(tour, true)
//        showToast("Get Bookmark")
//    }
//
//    fun deleteTour(tour: EntityTourism) = viewModelScope.launch {
//        repo.setTourBookmark(tour, false)
//        showToast("Delete Bookmark")
//    }

    fun showHomeList() = viewModelScope.launch {
        _isLoading.value = true
        try {
            _homeTourList.value = repo.getHomeList()
        } catch (e: Exception) {
            Log.d("HomeViewModel", "Load Error : ${e.message}")
            showToast("Gagal menampilkan daftar list : ${e.message}")
        }
        _isLoading.value = false
    }

    private fun showToast(msg: String) {
        _toastText.value = Event(msg)
    }
}