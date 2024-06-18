package com.bangkit2024.tourid.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bangkit2024.tourid.databinding.FragmentAccountBinding
import com.bangkit2024.tourid.ui.login.LoginActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding
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
        if (firebaseUser == null) {
            binding?.apply {
                signInButtonAccount.visibility = View.VISIBLE
                card.visibility = View.GONE
                btnLogout.visibility = View.GONE

                signInButtonAccount.setOnClickListener {
                    val intentLogin = Intent(requireContext(), LoginActivity::class.java)
                    intentLogin.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intentLogin)
                    activity?.finish()
                }
            }
        } else {
            val email = firebaseUser.email
            val username = firebaseUser.displayName
            val photo = firebaseUser.photoUrl
            val uid = firebaseUser.uid

            binding?.signInButtonAccount?.visibility = View.GONE
            binding?.card?.visibility = View.VISIBLE
            binding?.btnLogout?.visibility = View.VISIBLE

            binding?.tvEmail?.text = email
            binding?.tvEmailUsername?.text = username
            if (photo != null) {
                binding?.imvEmail?.let { Glide.with(this).load(photo).into(it) }
            }

        }

        binding?.btnLogout?.setOnClickListener {
            logOut()
        }
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