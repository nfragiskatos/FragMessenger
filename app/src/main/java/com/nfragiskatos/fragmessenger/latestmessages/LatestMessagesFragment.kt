package com.nfragiskatos.fragmessenger.latestmessages

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.nfragiskatos.fragmessenger.R
import com.nfragiskatos.fragmessenger.databinding.FragmentLatestMessagesBinding

class LatestMessagesFragment : Fragment() {

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

        return inflater.inflate(R.layout.fragment_latest_messages, container, false)
    }

}