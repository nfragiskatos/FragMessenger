package com.nfragiskatos.fragmessenger.chatlog

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nfragiskatos.fragmessenger.R

class ChatLogFragment : Fragment() {

    companion object {
        fun newInstance() = ChatLogFragment()
    }

    private lateinit var viewModel: ChatLogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_log, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChatLogViewModel::class.java)
        // TODO: Use the ViewModel
    }

}