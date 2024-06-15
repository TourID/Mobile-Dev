package com.bangkit2024.tourid.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit2024.tourid.repository.TourRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

//class AccountViewModel(private val repo: TourRepository) : ViewModel() {
//
//    private val _account = MutableLiveData<FirebaseUser?>()
//    val account: LiveData<FirebaseUser?> get() = _account
//
//    init {
//        _account.value = repo.getAccountUser()
//    }
//
//    fun getSignOut() = viewModelScope.launch {
//        repo.signOut()
//        _account.value = null
//    }
//}