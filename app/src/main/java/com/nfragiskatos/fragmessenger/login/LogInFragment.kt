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
import com.nfragiskatos.fragmessenger.R

import com.nfragiskatos.fragmessenger.databinding.FragmentLogInBinding
import java.lang.Exception

private const val TAG = "LogInFragment"

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

        viewModel.navigateToLatestMessagesScreen.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                this.findNavController()
                    .navigate(LogInFragmentDirections.actionLogInFragmentToLatestMessagesFragment())
                viewModel.displayLatestMessagesScreenComplete()
            }
        })

        viewModel.logMessage.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, it)
        })

        viewModel.notification.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT)
                .show()
        })

        viewModel.status.observe(viewLifecycleOwner, Observer {status ->
            when (status) {
                LogInStatus.LOADING -> {
//                    binding.buttonLogInLogIn.visibility = View.GONE
//                    binding.textEmailLogIn.visibility = View.GONE
//                    binding.textPasswordLogIn.visibility = View.GONE
                    binding.textPasswordLogIn.isEnabled = false
                    binding.textEmailLogIn.isEnabled = false
                    binding.buttonLogInLogIn.isEnabled = false
                }
                LogInStatus.DONE, LogInStatus.ERROR -> {
//                    binding.buttonLogInLogIn.visibility = View.VISIBLE
//                    binding.textEmailLogIn.visibility = View.VISIBLE
//                    binding.textPasswordLogIn.visibility = View.VISIBLE
                    binding.buttonLogInLogIn.isEnabled = true
                    binding.textEmailLogIn.isEnabled = true
                    binding.textPasswordLogIn.isEnabled = true
                }
            }
        })

        return binding.root
    }
}
