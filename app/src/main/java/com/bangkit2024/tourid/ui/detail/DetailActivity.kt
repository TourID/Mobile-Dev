package com.bangkit2024.tourid.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit2024.tourid.R
import com.bangkit2024.tourid.adapter.ReviewAdapter
import com.bangkit2024.tourid.databinding.ActivityDetailBinding
import com.bangkit2024.tourid.di.InjectionTourism
import com.bangkit2024.tourid.ui.bookmarks.BookmarkViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private val detailVM by viewModels<DetailViewModel> {
        InjectionTourism.provideViewModelFactory(this)
    }
    private val bookmarkVM by viewModels<BookmarkViewModel> {
        InjectionTourism.provideViewModelFactory(this)
    }
    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var mMap: GoogleMap
    private lateinit var reviewAdapter: ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.location_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setupRecyclerView()
        observeViewModel()
        setupListeners()

        val placeId = intent.getIntExtra(KEY_DETAIL, 0)
        if (placeId != 0) {
            detailVM.fetchDetail(placeId)
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            if (userId.isNotEmpty()) {
                bookmarkVM.getBookmarks(userId)
            } else {
                Toast.makeText(this, "User ID not available. Cannot fetch bookmarks.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupListeners() {
        val placeId = intent.getIntExtra(KEY_DETAIL, 0)
        detailBinding.btnArrow.setOnClickListener {
            finish()
        }

        detailBinding.btnBookmark.setOnClickListener {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            if (userId.isNotEmpty()) {
                bookmarkVM.bookmarks.value?.let { bookmarks ->
                    val isBookmarked = bookmarks.any { it.placeId == placeId }
                    if (isBookmarked) {
                        bookmarkVM.deleteBookmark(userId, placeId)
                    } else {
                        bookmarkVM.addBookmark(userId, placeId)
                    }
                }
            } else {
                Toast.makeText(this, "User ID not available. Cannot add or remove bookmark.", Toast.LENGTH_SHORT).show()
            }
        }

        detailBinding.postReviewButton.setOnClickListener {
            val review = detailBinding.reviewInput.text.toString()
            val rating = detailBinding.ratingBar.rating.toDouble()
            if (placeId != 0) {
                detailVM.addReview(review, rating, placeId)
            }
        }
    }

    private fun observeViewModel() {
        detailVM.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        detailVM.toastText.observe(this) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

        detailVM.detail.observe(this) { detail ->
            if (detail != null) {
                detailBinding.titlePlace.text = detail.placeName
                detailBinding.location.text = detail.city
                detailBinding.description.text = detail.description
                detailBinding.reviews.text = detail.ratingLoc.toString()
                Glide.with(this@DetailActivity)
                    .load(detail.imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(detailBinding.ivDetail)

                val location = LatLng(detail.lat as Double, detail.jsonMemberLong as Double)
                mMap.clear()
                mMap.addMarker(MarkerOptions().position(location).title(detail.placeName))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))

                detail.reviews?.let { reviewAdapter.submitList(it.filterNotNull()) }
            }
        }

        bookmarkVM.bookmarks.observe(this) { bookmarks ->
            val placeId = intent.getIntExtra(KEY_DETAIL, 0)
            val isBookmarked = bookmarks?.any { it.placeId == placeId }
            detailBinding.btnBookmark.setImageResource(
                if (isBookmarked == true) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark_border
            )
        }

        bookmarkVM.bookmarkAdded.observe(this) { bookmarkAdded ->
            if (bookmarkAdded) {
                Toast.makeText(this, "Bookmark added successfully", Toast.LENGTH_SHORT).show()
                detailBinding.btnBookmark.setImageResource(R.drawable.ic_bookmark_filled)
            } else {
                Toast.makeText(this, "Failed to add bookmark", Toast.LENGTH_SHORT).show()
            }
        }

        bookmarkVM.bookmarkRemoved.observe(this) { bookmarkRemoved ->
            if (bookmarkRemoved) {
                Toast.makeText(this, "Bookmark removed successfully", Toast.LENGTH_SHORT).show()
                detailBinding.btnBookmark.setImageResource(R.drawable.ic_bookmark_border)
            } else {
                Log.d(this.toString(), "Failed to remove bookmark")
                Toast.makeText(this, "Failed to remove bookmark", Toast.LENGTH_SHORT).show()
            }
        }

        detailVM.reviewAdded.observe(this) { reviewAdded ->
            if (reviewAdded) {
                Toast.makeText(this, "Review added successfully", Toast.LENGTH_SHORT).show()
                detailBinding.reviewInput.text.clear()
                detailBinding.ratingBar.rating = 0f
            } else {
                Toast.makeText(this, "Failed to add review", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        detailBinding.pbDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView() {
        reviewAdapter = ReviewAdapter()
        detailBinding.rvReviews.apply {
            layoutManager = LinearLayoutManager(this@DetailActivity)
            adapter = reviewAdapter
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
    }

    companion object {
        const val KEY_DETAIL = "key_detail"
    }
}