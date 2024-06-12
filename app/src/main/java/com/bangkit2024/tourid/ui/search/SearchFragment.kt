package com.bangkit2024.tourid.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit2024.tourid.adapter.AdapterItem
import com.bangkit2024.tourid.databinding.FragmentSearchBinding
import com.bangkit2024.tourid.di.Injection
import com.bangkit2024.tourid.repository.Result

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding
    private lateinit var searchAdapter: AdapterItem
    private val searchVM by viewModels<SearchViewModel> {
        Injection.provideViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchAdapter = AdapterItem {user ->
            if (user.isBookmarked) searchVM.deleteNews(user) else searchVM.saveNews(user)
        }

        binding?.rvSearch?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = searchAdapter
        }

        searchVM.isLoading.observe(viewLifecycleOwner) { loading ->
            showLoading(loading)
        }

        searchVM.getHeadlineNews().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.pbSearch?.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding?.pbSearch?.visibility = View.GONE
                        val newsData = result.data
                        searchAdapter.submitList(newsData)
                    }
                    is Result.Error -> {
                        binding?.pbSearch?.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "Terjadi Kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("HomeFragment", "{${result.error}}")
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.pbSearch?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}