package com.nfragiskatos.fragmessenger.chatlog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nfragiskatos.fragmessenger.MainViewModel
import com.nfragiskatos.fragmessenger.databinding.FragmentChatLogBinding

class ChatLogFragment : Fragment() {

    private val TAG = "ChatLogFragment"

    private lateinit var binding: FragmentChatLogBinding

    companion object {
        fun newInstance() = ChatLogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val mainViewModel = activity?.let { ViewModelProvider(it).get(MainViewModel::class.java) }
        mainViewModel?.updateActionBarTitle("Chat Log")

        val user = ChatLogFragmentArgs.fromBundle(requireArguments()).user

        Log.d(TAG, "New user: ${user.username}")

        val chatLogViewModelFactory = ChatLogViewModelFactory(user)
        val viewModel = ViewModelProvider(this, chatLogViewModelFactory).get(ChatLogViewModel::class.java)

        mainViewModel?.updateActionBarTitle(viewModel.contact.username)

        binding = FragmentChatLogBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.recyclerviewChatHistoryChatLog.adapter = MessageListAdapter()


        viewModel.getData()
        return binding.root
    }
}