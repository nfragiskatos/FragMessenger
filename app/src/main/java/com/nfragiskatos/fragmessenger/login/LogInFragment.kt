package com.nfragiskatos.fragmessenger.login

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

import com.nfragiskatos.fragmessenger.databinding.FragmentLogInBinding
import java.lang.Exception

class LogInFragment : Fragment() {

    private val viewModel: LogInViewModel by lazy {
        ViewModelProvider(this).get(LogInViewModel::class.java)
    }

    companion object {
        fun newInstance() = LogInFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentLogInBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.buttonLogInLogIn.setOnClickListener {
            performLogIn()
        }

        viewModel.navigateToLatestMessagesScreen.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                this.findNavController()
                    .navigate(LogInFragmentDirections.actionLogInFragmentToLatestMessagesFragment())
                viewModel.displayLatestMessagesScreenComplete()
            }
        })

        return binding.root
    }

    private fun performLogIn() {
        val email = viewModel.email.value
        val password = viewModel.password.value

        if (email == null || password == null) {
            Toast.makeText(context, "Please enter text in email and password.", Toast.LENGTH_SHORT)
                .show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                onCompletedLogIn(it)
            }.addOnFailureListener {
            onFailedLogIn(it)
        }
    }

    private fun onCompletedLogIn(result: Task<AuthResult>) {
        if (!result.isSuccessful) {
            Log.d("LogInFragment", "Completed with failure: ${result.exception}")
            return
        }

        Log.d(
            "LogInFragment",
            "Successfully logged in user with\nuid: ${result.result?.user?.uid}\nemail: ${result.result?.user?.email}"
        )
        viewModel.displayLatestMessagesScreen()
    }

    private fun onFailedLogIn(result: Exception) {
        Toast.makeText(context, "Failed to create user: ${result.message}", Toast.LENGTH_SHORT)
            .show()
        Log.d("LogInFragment", "Failed to log in user: ${result.message}")
    }
}
