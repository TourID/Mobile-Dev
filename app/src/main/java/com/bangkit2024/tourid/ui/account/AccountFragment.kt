package com.bangkit2024.tourid.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bangkit2024.tourid.adapter.AdapterItem
import com.bangkit2024.tourid.databinding.FragmentAccountBinding
import com.bangkit2024.tourid.di.Injection
import com.bangkit2024.tourid.ui.login.LoginActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding
    private lateinit var homeAdapter: AdapterItem
    private val accountVM by viewModels<AccountViewModel> {
        Injection.provideViewModelFactory(requireContext())
    }
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val firebaseUser = auth.currentUser
//        if (firebaseUser == null) {
//            // Not signed in, launch the Login activity
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//            return
//        }
        binding?.btnLogout?.setOnClickListener {
            logOut()
        }

        val email = firebaseUser?.email
        binding?.tvEmail?.text = email

        val username = firebaseUser?.displayName
        binding?.tvEmailUsername?.text = username

        val photo = firebaseUser?.photoUrl
        if (photo != null) {
            binding?.imvEmail?.let { Glide.with(this).load(photo).into(it) }
        }

        val uid = firebaseUser?.uid
        binding?.tvUid?.text = uid
    }

    private fun logOut() {
        lifecycleScope.launch {
            val credentialManager = CredentialManager.create(requireContext())
            auth.signOut()
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}