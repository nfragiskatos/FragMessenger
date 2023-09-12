package com.nfragiskatos.fragmessenger.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.nfragiskatos.fragmessenger.databinding.FragmentLogInBinding


private const val TAG = "LogInFragment"

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding

    private val viewModel: LogInViewModel by lazy {
        ViewModelProvider(this).get(LogInViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLogInBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.navigateToLatestMessagesScreen.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                this.findNavController()
                    .navigate(LogInFragmentDirections.actionLogInFragmentToLatestMessagesFragment())
                viewModel.displayLatestMessagesScreenComplete()
            }
        })

        binding.buttonLogInLogIn.setOnClickListener {
            val imm: InputMethodManager = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
            viewModel.performLogIn()
        }

        initObservers()
        return binding.root
    }

    private fun initObservers() {
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
                    setLoginEnabled(false)
                }
                else -> {
                    setLoginEnabled(true)
                }
            }
        })
    }

    private fun setLoginEnabled(isEnabled: Boolean) {
        binding.textPasswordLogIn.isEnabled = isEnabled
        binding.textEmailLogIn.isEnabled = isEnabled
        binding.buttonLogInLogIn.isEnabled = isEnabled
    }
}
