package com.bangkit2024.tourid.ui.home

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit2024.tourid.R
import com.bangkit2024.tourid.adapter.RecommendationAdapter
import com.bangkit2024.tourid.data.remote.response.WeatherResponse
import com.bangkit2024.tourid.databinding.FragmentHomeBinding
import com.bangkit2024.tourid.di.InjectionTourism
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var recommendationAdapter: RecommendationAdapter
    private val homeVM by viewModels<HomeViewModel> {
        InjectionTourism.provideViewModelFactory(requireContext())
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recommendationAdapter = RecommendationAdapter()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding?.apply {
            rvHome.layoutManager = LinearLayoutManager(context)
            rvHome.setHasFixedSize(true)
            rvHome.adapter = recommendationAdapter
        }

        homeVM.isLoading.observe(viewLifecycleOwner) { loading ->
            showLoading(loading)
        }

        homeVM.toastText.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                showToast(message)
            }
        }

        homeVM.weather.observe(viewLifecycleOwner) { weatherResponse ->
            updateUI(weatherResponse)
        }

        val userId = Firebase.auth.currentUser?.uid ?: ""
        homeVM.fetchRecommendations(userId)

        homeVM.recommendations.observe(viewLifecycleOwner) { recommendations ->
            recommendationAdapter.submitList(recommendations)
        }

        binding?.ivRefreshHome?.setOnClickListener {
            getMyLocation()
            homeVM.fetchRecommendations(userId)
        }

        getMyLocation()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            } else {
                showToast("Location permission denied")
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        homeVM.fetchWeatherByCoordinates(it.latitude, it.longitude)
                    } ?: run {
                        showToast("Failed to get location")
                    }
                }
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.pbHome?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun updateUI(weatherResponse: WeatherResponse) {
        binding?.apply {
            tvCity.text = weatherResponse.name

            val tempInCelsius = weatherResponse.main?.temp?.minus(273.15)
            tvTemp.text = getString(R.string.temperature, tempInCelsius)

            val tempMin = weatherResponse.main?.tempMin?.minus(273.15)
            val tempMax = weatherResponse.main?.tempMax?.minus(273.15)
            tvMinTemp.text = getString(R.string.min_temperature, tempMin)
            tvMaxTemp.text = getString(R.string.max_temperature, tempMax)

            tvWeatherDesc.text = weatherResponse.weather?.get(0)?.description

            val iconId = weatherResponse.weather?.get(0)?.icon
            val iconUrl = "https://openweathermap.org/img/w/$iconId.png"
            Glide.with(requireContext())
                .load(iconUrl)
                .into(weatherIconImageView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}