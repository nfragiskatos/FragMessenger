package com.nfragiskatos.fragmessenger.presentation.ui.chatlog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nfragiskatos.fragmessenger.R
import com.nfragiskatos.fragmessenger.databinding.FragmentChatLogBinding
import com.nfragiskatos.fragmessenger.presentation.ui.MainViewModel

private const val TAG = "ChatLogFragment"

class ChatLogFragment : Fragment() {

    private lateinit var binding: FragmentChatLogBinding

    private lateinit var viewModel: ChatLogViewModel

    companion object {
        fun newInstance() = ChatLogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d(TAG, "ViewModelStore: $viewModelStore")

        defaultViewModelProviderFactory
        val user = ChatLogFragmentArgs.fromBundle(
            requireArguments()
        ).user

        viewModel =
            ViewModelProvider(this, ChatLogViewModelFactory(user)).get(ChatLogViewModel::class.java)

        binding = FragmentChatLogBinding.inflate(inflater).also { binding ->
            binding.lifecycleOwner = this
            binding.viewModel = viewModel
            binding.recyclerviewChatHistoryChatLog.adapter = ChatLogListAdapter()
        }
        initObservers()

        viewModel.listenForMessages()
        initActionBarTitle()
        return binding.root
    }

    private fun initObservers() {
        viewModel.notification.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT)
                .show()
        }

        viewModel.logMessage.observe(viewLifecycleOwner) {
            Log.d(TAG, it)
        }

        viewModel.messageAdded.observe(viewLifecycleOwner) {
            if (it) {
                binding.recyclerviewChatHistoryChatLog.scrollToPosition((binding.recyclerviewChatHistoryChatLog.adapter as ChatLogListAdapter).itemCount - 1)
                viewModel.messageAddedComplete()
            }
        }
    }

    private fun initActionBarTitle() {
        val mainViewModel = activity?.let { ViewModelProvider(it).get(MainViewModel::class.java) }
        mainViewModel?.updateActionBarTitle(resources.getString(R.string.app_name))
    }
}