package com.bangkit2024.tourid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.tourid.data.remote.response.Recommendation
import com.bangkit2024.tourid.data.remote.response.TourResponseItem
import com.bangkit2024.tourid.data.remote.response.WeatherResponse
import com.bangkit2024.tourid.repository.Result
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

    private val _weather = MutableLiveData<WeatherResponse>()
    val weather: LiveData<WeatherResponse> get() = _weather

    private val _recommendations = MutableLiveData<List<Recommendation>>()
    val recommendations: LiveData<List<Recommendation>> get() = _recommendations

    fun fetchWeatherByCoordinates(lat: Double, lon: Double) {
        repo.getWeatherByCoordinates(lat, lon) { weatherResponse ->
            _weather.postValue(weatherResponse!!)
        }
    }

    fun fetchRecommendations(userId: String) = viewModelScope.launch {
        _isLoading.value = true
        repo.getRecommendations(userId).observeForever { result ->
            when (result) {
                is Result.Loading -> _isLoading.value = true
                is Result.Success -> {
                    _recommendations.value = result.data
                    _isLoading.value = false
                }
                is Result.Error -> {
                    _isLoading.value = false
                    showToast("Failed to fetch recommendations: ${result.error}")
                }
            }
        }
    }

    private fun showToast(msg: String) {
        _toastText.value = Event(msg)
    }
}
