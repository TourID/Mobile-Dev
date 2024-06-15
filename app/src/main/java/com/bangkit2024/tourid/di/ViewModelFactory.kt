package com.bangkit2024.tourid.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit2024.tourid.repository.TourRepository
import com.bangkit2024.tourid.ui.account.AccountViewModel
import com.bangkit2024.tourid.ui.bookmarks.BookmarkViewModel
import com.bangkit2024.tourid.ui.home.HomeViewModel
import com.bangkit2024.tourid.ui.search.SearchViewModel

class ViewModelFactory(private val repository: TourRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository) as T
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel(repository) as T
            modelClass.isAssignableFrom(BookmarkViewModel::class.java) -> BookmarkViewModel(repository) as T
            modelClass.isAssignableFrom(AccountViewModel::class.java) -> AccountViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}