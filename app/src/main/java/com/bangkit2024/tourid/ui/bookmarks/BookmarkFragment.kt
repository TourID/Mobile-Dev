package com.bangkit2024.tourid.ui.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit2024.tourid.adapter.AdapterItem
import com.bangkit2024.tourid.databinding.FragmentBookmarkBinding
import com.bangkit2024.tourid.di.Injection

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding
    private lateinit var bookmarkAdapter: AdapterItem
    private val bookmarkVM by viewModels<BookmarkViewModel> {
        Injection.provideViewModelFactory(requireContext())
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

        bookmarkAdapter = AdapterItem { user ->
            if (user.isBookmarked) bookmarkVM.deleteNews(user) else bookmarkVM.saveNews(user)
        }

        binding?.rvSearch?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = bookmarkAdapter
        }

        bookmarkVM.isLoading.observe(viewLifecycleOwner) { loading ->
            showLoading(loading)
        }

        bookmarkVM.getBookmarkedNews().observe(viewLifecycleOwner) { bookmark ->
            binding?.pbBookmark?.visibility = View.GONE
            bookmarkAdapter.submitList(bookmark)
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