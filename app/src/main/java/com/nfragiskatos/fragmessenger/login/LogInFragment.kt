package com.nfragiskatos.fragmessenger.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nfragiskatos.fragmessenger.databinding.FragmentLogInBinding
import com.nfragiskatos.fragmessenger.utility.hideKeyboard


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

        binding.buttonLogInLogIn.setOnClickListener {
            hideKeyboard(requireActivity())
            viewModel.performLogIn()
        }
        initObservers()
        return binding.root
    }

    private fun initObservers() {
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
        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
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
