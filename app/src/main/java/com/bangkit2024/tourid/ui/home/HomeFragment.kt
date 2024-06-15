package com.bangkit2024.tourid.ui.home

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
import com.bangkit2024.tourid.databinding.FragmentHomeBinding
import com.bangkit2024.tourid.di.InjectionTourism
import com.bangkit2024.tourid.repository.Result

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var homeAdapter: AdapterItem
    private val homeVM by viewModels<HomeViewModel> {
        InjectionTourism.provideViewModelFactory(requireContext())
    }

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

//        homeAdapter = AdapterItem {user ->
//            if (user.isBookmarked) homeVM.deleteNews(user) else homeVM.saveNews(user)
//        }
//
//        binding?.rvHome?.apply {
//            layoutManager = LinearLayoutManager(context)
//            setHasFixedSize(true)
//            adapter = homeAdapter
//        }
//
//        homeVM.isLoading.observe(viewLifecycleOwner) { loading ->
//            showLoading(loading)
//        }
//
//        homeVM.getHeadlineNewsHome().observe(viewLifecycleOwner) { result ->
//            if (result != null) {
//                when (result) {
//                    is Result.Loading -> {
//                        binding?.pbHome?.visibility = View.VISIBLE
//                    }
//                    is Result.Success -> {
//                        binding?.pbHome?.visibility = View.GONE
//                        val newsData = result.data
//                        homeAdapter.submitList(newsData)
//                    }
//                    is Result.Error -> {
//                        binding?.pbHome?.visibility = View.GONE
//                        Toast.makeText(
//                            context,
//                            "Terjadi Kesalahan" + result.error,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        Log.e("HomeFragment", "{${result.error}}")
//                    }
//                }
//            }
//        }

        homeAdapter = AdapterItem { user ->
            if (user.isBookmarked) homeVM.deleteTour(user) else homeVM.saveTour(user)
        }

        binding?.rvHome?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = homeAdapter
        }

        homeVM.isLoading.observe(viewLifecycleOwner) { loading ->
            showLoading(loading)
        }

        homeVM.getTourism().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.pbHome?.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding?.pbHome?.visibility = View.GONE
                        val newsData = result.data
                        homeAdapter.submitList(newsData)
                    }

                    is Result.Error -> {
                        binding?.pbHome?.visibility = View.GONE
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
        binding?.pbHome?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}