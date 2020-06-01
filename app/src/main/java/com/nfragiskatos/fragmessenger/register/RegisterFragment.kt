package com.nfragiskatos.fragmessenger.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

import com.nfragiskatos.fragmessenger.databinding.FragmentRegisterBinding
import java.lang.Exception

class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    private lateinit var binding: FragmentRegisterBinding

    companion object {
        fun newInstance() = RegisterFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.textAlreadyHaveAccountRegister.setOnClickListener {
            viewModel.displayLogInScreen()
        }

        viewModel.navigateToLogInScreen.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                this.findNavController()
                        .navigate(RegisterFragmentDirections.actionFragmentRegisterToLogInFragment())
                viewModel.displayLogInScreenComplete()
            }
        })

        binding.buttonRegisterRegister.setOnClickListener {
            performRegistration()
        }


        setHasOptionsMenu(true)
        return binding.root
    }

    private fun performRegistration() {
        val email = viewModel.email.value
        val password = viewModel.password.value

        if (email == null || password == null) {
            Toast.makeText(context, "Please enter text in email and password.", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { result ->
                    onCompletedRegistration(result)
                }
                .addOnFailureListener { result ->
                    onFailedRegistration(result)
                }
    }

    private fun onCompletedRegistration(result: Task<AuthResult>) {
        if (!result.isSuccessful) {
            Log.d("RegisterFragment", "Completed with failure: ${result.exception}")
            return
        }

        Log.d("RegisterFragment",
                "Successfully created user with\nuid: ${result.result?.user?.uid}\nemail: ${result.result?.user?.email}"
        )
    }

    private fun onFailedRegistration(result: Exception) {
        Log.d("RegisterFragment", "Failed to create user: ${result.message}")
    }
}
