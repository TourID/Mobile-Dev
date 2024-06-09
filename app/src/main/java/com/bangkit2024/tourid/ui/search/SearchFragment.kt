package com.bangkit2024.tourid.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit2024.tourid.databinding.FragmentSearchBinding
import com.bangkit2024.tourid.di.Injection

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val searchVM by viewModels<SearchViewModel> {
        Injection.provideViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}