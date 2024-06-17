package com.bangkit2024.tourid.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit2024.tourid.data.remote.response.DetailResponse
import com.bangkit2024.tourid.repository.TourRepository


class DetailViewModel : ViewModel() {
    private val repository = TourRepository()

    val detail: LiveData<DetailResponse> get() = repository.detail

    fun fetchDetail(id: String) {
        repository.fetchDetail(id)
    }
}