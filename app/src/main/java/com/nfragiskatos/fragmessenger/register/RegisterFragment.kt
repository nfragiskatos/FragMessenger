package com.nfragiskatos.fragmessenger.register

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nfragiskatos.fragmessenger.databinding.FragmentRegisterBinding
import com.nfragiskatos.fragmessenger.utility.hideKeyboard

private const val TAG = "RegisterFragment"

class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRegisterBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.buttonSelectPhotoRegister.setOnClickListener {
            Log.d(TAG, "Try and show photo selector")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
        binding.buttonRegisterRegister.setOnClickListener {
            hideKeyboard(requireActivity())
            viewModel.performRegistration()
        }
        initObservers()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun initObservers() {
        viewModel.navigateToLogInScreen.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                this.findNavController()
                    .navigate(RegisterFragmentDirections.actionFragmentRegisterToLogInFragment())
                viewModel.displayLogInScreenComplete()
            }
        })

        viewModel.navigateToLatestMessagesScreen.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                this.findNavController()
                    .navigate(RegisterFragmentDirections.actionRegisterFragmentToLatestMessagesFragment())
                viewModel.displayLatestMessagesScreenComplete()
            }
        })

        viewModel.logMessage.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, it)
        })

        viewModel.notification.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG)
                .show()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d(TAG, "Photo was selected")
            viewModel.selectedPhotoUri.value = data.data

            val bitmap = when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> {
                    activity?.contentResolver?.let { contentResolver ->
                        viewModel.selectedPhotoUri.value?.let { photoUri ->
                            val source = ImageDecoder.createSource(
                                contentResolver,
                                photoUri
                            )
                            ImageDecoder.decodeBitmap(source)
                        }
                    }
                }

                else -> MediaStore.Images.Media.getBitmap(
                    activity?.contentResolver,
                    viewModel.selectedPhotoUri.value
                )
            }


            binding.imageViewSelectPhotoRegister.setImageBitmap(bitmap)
            binding.buttonSelectPhotoRegister.alpha = 0f
        }
    }
}
