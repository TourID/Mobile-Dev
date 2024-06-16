package com.bangkit2024.tourid.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit2024.tourid.R
import com.bangkit2024.tourid.adapter.AdapterItem
import com.bangkit2024.tourid.databinding.FragmentSearchBinding
import com.bangkit2024.tourid.di.InjectionTourism
import com.bangkit2024.tourid.repository.Result

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding
    private lateinit var searchAdapter: AdapterItem
    private val searchVM by viewModels<SearchViewModel> {
        InjectionTourism.provideViewModelFactory(requireContext())
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

        searchAdapter = AdapterItem { user ->
            if (user.isBookmarked) searchVM.deleteTour(user) else searchVM.saveTour(user)
        }

        binding?.rvSearch?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = searchAdapter
        }

        searchVM.isLoading.observe(viewLifecycleOwner) { loading ->
            showLoading(loading)
        }

        val buttons = listOf(
            binding?.btnCategoryAll,
            binding?.btnCategoryBahari,
            binding?.btnCategoryBudaya,
            binding?.btnCategoryCagara,
            binding?.btnCategoryHiburan,
            binding?.btnCategoryIbadah,
            binding?.btnCategoryPerbelanjaan
        )

        for (btns in buttons) {
            btns?.setOnClickListener {
                for (btn in buttons) {
                    btn?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                    btn?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                }

                btns.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.teal_200
                    )
                )
                btns.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )

                val category = btns.text.toString().lowercase()

                searchVM.getTourism().observe(viewLifecycleOwner) { result ->
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
        }

        binding?.btnCategoryAll?.performClick()
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.pbSearch?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}