package com.bangkit2024.tourid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit2024.tourid.R
import com.bangkit2024.tourid.adapter.AdapterItem
import com.bangkit2024.tourid.data.remote.response.WeatherResponse
import com.bangkit2024.tourid.databinding.FragmentHomeBinding
import com.bangkit2024.tourid.di.InjectionTourism
import com.bumptech.glide.Glide

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeAdapter: AdapterItem
    private val homeVM by viewModels<HomeViewModel> {
        InjectionTourism.provideViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi RecyclerView dan Adapter
        homeAdapter = AdapterItem()
        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = homeAdapter
        }

        val latitude = -6.2349
        val longitude = 106.9896

        homeVM.fetchWeatherByCoordinates(latitude, longitude)

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
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbHome.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun updateUI(weatherResponse: WeatherResponse) {
        binding.apply {
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