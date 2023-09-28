package com.nfragiskatos.fragmessenger.presentation.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nfragiskatos.fragmessenger.databinding.FragmentLogInBinding
import com.nfragiskatos.fragmessenger.utility.LoadingStatus
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

        binding = FragmentLogInBinding.inflate(inflater).also {
            it.lifecycleOwner = this
            it.viewModel = viewModel
            it.buttonLogInLogIn.setOnClickListener {
                hideKeyboard(requireActivity())
                viewModel.performLogIn()
            }
        }
        initObservers()
        return binding.root
    }

    private fun initObservers() {
        viewModel.navigateToLatestMessagesScreen.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                this.findNavController()
                    .navigate(LogInFragmentDirections.actionLogInFragmentToLatestMessagesFragment())
                viewModel.displayLatestMessagesScreenComplete()
            }
        }
        viewModel.logMessage.observe(viewLifecycleOwner) {
            Log.d(TAG, it)
        }
        viewModel.notification.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG)
                .show()
        }
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                LoadingStatus.LOADING -> {
                    setLoginEnabled(false)
                }

                else -> {
                    setLoginEnabled(true)
                }
            }
        }
    }

    private fun setLoginEnabled(isEnabled: Boolean) {
        binding.apply {
            textPasswordLogIn.isEnabled = isEnabled
            textEmailLogIn.isEnabled = isEnabled
            buttonLogInLogIn.isEnabled = isEnabled
        }
    }
}
