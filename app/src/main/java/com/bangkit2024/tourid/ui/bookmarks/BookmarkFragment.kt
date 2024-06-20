package com.bangkit2024.tourid.ui.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit2024.tourid.adapter.AdapterItem
import com.bangkit2024.tourid.data.remote.response.TourResponseItem
import com.bangkit2024.tourid.databinding.FragmentBookmarkBinding
import com.bangkit2024.tourid.di.InjectionTourism
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding
    private lateinit var bookmarkAdapter: AdapterItem
    private val bookmarkVM by viewModels<BookmarkViewModel> {
        InjectionTourism.provideViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = Firebase.auth.currentUser?.uid ?: ""

        bookmarkAdapter = AdapterItem()
        binding?.apply {
            rvBookmarks.layoutManager = LinearLayoutManager(context)
            rvBookmarks.setHasFixedSize(true)
            rvBookmarks.adapter = bookmarkAdapter
        }

        bookmarkVM.isLoading.observe(viewLifecycleOwner) { loading ->
            showLoading(loading)
        }

        bookmarkVM.toastText.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        bookmarkVM.bookmarks.observe(viewLifecycleOwner) { bookmarks ->
            val items = arrayListOf<TourResponseItem>()
            bookmarks?.map {
                val item = TourResponseItem(
                    placeId = it.placeId,
                    city = it.city,
                    imageUrl = it.imageUrl,
                    ratingLoc = it.ratingLoc,
                    category = it.category,
                    placeName = it.placeName
                )
                items.add(item)
            }
            bookmarkAdapter.submitList(items)
            if (items.isEmpty()) {
                binding?.ivErrorFavorite?.visibility = View.VISIBLE
            } else {
                binding?.ivErrorFavorite?.visibility = View.GONE
            }
        }

        bookmarkVM.getBookmarks(userId)

        binding?.ivRefreshBookmark?.setOnClickListener {
            bookmarkVM.getBookmarks(userId)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.pbBookmark?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}