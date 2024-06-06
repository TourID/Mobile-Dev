package com.bangkit2024.tourid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.tourid.Event
import com.bangkit2024.tourid.data.ProductsItem
import com.bangkit2024.tourid.repository.TourRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: TourRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    private val _listItem = MutableLiveData<List<ProductsItem>>()
    val listItem: LiveData<List<ProductsItem>> = _listItem

    private val _resultSearch = MutableLiveData<List<ProductsItem>>()
    val resultSearch: LiveData<List<ProductsItem>> = _resultSearch

    fun setList() = viewModelScope.launch {
        _isLoading.value = true
        try {
            _listItem.value = repo.getItemList()
        } catch (e: Exception) {
            showToast("Gagal menampilkan daftar user : ${e.message}")
        }
        _isLoading.value = false
    }

    fun searchUser(query: String) = viewModelScope.launch {
        _isLoading.value = true
        try {
            val result = repo.searchUser(query)
            if (result.isEmpty()) {
                showToast("Username $query tidak terdaftar atau tidak ada")
                _resultSearch.value = emptyList()
            } else {
                showToast(query)
                _resultSearch.value = result
            }
        } catch (e: Exception) {
            showToast("Gagal menampilkan hasil pencarian : ${e.message}")
        }

        _isLoading.value = false
    }

    private fun showToast(msg: String) {
        _toastText.value = Event(msg)
    }
}