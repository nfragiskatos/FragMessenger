package com.nfragiskatos.fragmessenger.latestmessages

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nfragiskatos.fragmessenger.R
import com.nfragiskatos.fragmessenger.databinding.FragmentLatestMessagesBinding

class LatestMessagesFragment : Fragment() {

    private val TAG = "LatestMessagesFragment"

    private val viewModel: LatestMessagesViewModel by lazy {
        ViewModelProvider(this).get(LatestMessagesViewModel::class.java)
    }

    private lateinit var binding: FragmentLatestMessagesBinding

    companion object {
        fun newInstance() = LatestMessagesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLatestMessagesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        Firebase.auth.addAuthStateListener {
            if (it.currentUser == null) {
                viewModel.displayRegisterScreen()
            } else {
                Log.d(TAG, "${it.currentUser?.email} is already signed in")
            }
        }

        viewModel.navigateToRegisterScreen.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                findNavController().navigate(LatestMessagesFragmentDirections.actionLatestMessagesFragmentToRegisterFragment())
                viewModel.displayRegisterScreenComplete()
            }
        })

        binding.buttonTestLatestMessages.setOnClickListener {
            Log.d(TAG, "Signing out...")
            Firebase.auth.signOut()
        }

        return binding.root
    }

}