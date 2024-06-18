package com.bangkit2024.tourid.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.tourid.R
import com.bangkit2024.tourid.databinding.ActivityDetailBinding
import com.bangkit2024.tourid.di.InjectionTourism
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private val detailVM by viewModels<DetailViewModel> {
        InjectionTourism.provideViewModelFactory(this)
    }
    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.location_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
    }
}