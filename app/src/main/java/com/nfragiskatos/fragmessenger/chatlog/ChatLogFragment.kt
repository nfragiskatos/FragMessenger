package com.nfragiskatos.fragmessenger.chatlog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nfragiskatos.fragmessenger.databinding.FragmentChatLogBinding

class ChatLogFragment : Fragment() {

    private val TAG = "ChatLogFragment"

    private val viewModel: ChatLogViewModel by lazy {
        ViewModelProvider(this).get(ChatLogViewModel::class.java)
    }

    private lateinit var binding: FragmentChatLogBinding

    companion object {
        fun newInstance() = ChatLogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChatLogBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        return binding.root
    }
}