package com.bangkit2024.tourid.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bangkit2024.tourid.R
import com.bangkit2024.tourid.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding:ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val placeId = intent.getStringExtra("PLACE_ID") ?: return

        viewModel.fetchDetail(placeId)

        viewModel.detail.observe(this, Observer { detail ->
            detail?.let {
                // Update the UI with detail data
                binding.title.text = it.placeName
                binding.description.text = it.description
                // And so on for other fields
            }
        })
    }
}