package com.bangkit2024.tourid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit2024.tourid.AdapterItem
import com.bangkit2024.tourid.data.ProductsItem
import com.bangkit2024.tourid.databinding.FragmentHomeBinding
import com.bangkit2024.tourid.di.ViewModelFactory

class HomeFragment : Fragment() {

    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterItem
    private val homeVM by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AdapterItem()
        binding.rvHome.adapter = adapter

        homeVM.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        homeVM.setList()
        homeVM.listItem.observe(viewLifecycleOwner) { list ->
            setListUser(list)
        }
    }

    private fun setListUser(list: List<ProductsItem>) = adapter.submitList(list)

    private fun showLoading(isLoading: Boolean) {
        binding.pbHome.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}