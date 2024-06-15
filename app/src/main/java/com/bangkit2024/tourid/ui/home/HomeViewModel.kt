package com.bangkit2024.tourid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.tourid.Event
import com.bangkit2024.tourid.data.local.entity.EntityTour
import com.bangkit2024.tourid.data.remote.response.GithubUsersResponseItem
import com.bangkit2024.tourid.repository.TourRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: TourRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listUser = MutableLiveData<List<GithubUsersResponseItem>>()
    val listUser: LiveData<List<GithubUsersResponseItem>> = _listUser

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    private val _resultSearch = MutableLiveData<List<GithubUsersResponseItem>>()
    val resultUser: LiveData<List<GithubUsersResponseItem>> = _resultSearch

//    fun showUser() = viewModelScope.launch {
//        _isLoading.value = true
//        try {
//            _listUser.value = repo.getUserList()
//        } catch (e: Exception) {
//            showToast("Gagal menampilkan daftar user : ${e.message}")
//        }
//        _isLoading.value = false
//    }
//
//    fun searchUser(query: String) = viewModelScope.launch {
//        _isLoading.value = true
//        try {
//            val result = repo.searchUser(query)
//            if (result.isEmpty()) {
//                showToast("Username $query tidak terdaftar atau tidak ada")
//                _resultSearch.value = emptyList()
//            } else {
//                showToast(query)
//                _resultSearch.value = result
//            }
//        } catch (e: Exception) {
//            showToast("Gagal menampilkan hasil pencarian : ${e.message}")
//        }
//
//        _isLoading.value = false
//    }

    fun getHeadlineNews() = repo.getHeadlineNews()

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