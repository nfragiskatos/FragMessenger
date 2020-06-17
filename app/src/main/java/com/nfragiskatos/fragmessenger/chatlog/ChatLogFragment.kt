package com.nfragiskatos.fragmessenger.chatlog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
        val user = ChatLogFragmentArgs.fromBundle(requireArguments()).user
        val chatLogViewModelFactory = ChatLogViewModelFactory(user)
        val viewModel =
            ViewModelProvider(this, chatLogViewModelFactory).get(ChatLogViewModel::class.java)

        mainViewModel?.updateActionBarTitle(viewModel.contact.username)
        binding = FragmentChatLogBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerviewChatHistoryChatLog.adapter = MessageListAdapter()

        viewModel.notification.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT)
                .show()
        })

        viewModel.logMessage.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, it)
        })

        viewModel.messageAdded.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.recyclerviewChatHistoryChatLog.scrollToPosition((binding.recyclerviewChatHistoryChatLog.adapter as MessageListAdapter).itemCount - 1)
                viewModel.messageAddedComplete()
            }
        })

        viewModel.listenForMessages()
        return binding.root
    }
}