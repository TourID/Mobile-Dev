package com.bangkit2024.tourid.ui.home

import android.os.Bundle
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

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var homeAdapter: AdapterItem
    private val homeVM by viewModels<HomeViewModel> {
        InjectionTourism.provideViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeAdapter = AdapterItem()
        binding?.rvHome?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = homeAdapter
        }

        homeVM.isLoading.observe(viewLifecycleOwner) { loading ->
            showLoading(loading)
        }

        homeVM.toastText.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                showToast(message)
            }
        }

        homeVM.showHomeList()

        homeVM.homeTourList.observe(viewLifecycleOwner) {response ->
            homeAdapter.submitList(response)
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding?.pbHome?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(m: String) {
        Toast.makeText(requireContext(), m, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}