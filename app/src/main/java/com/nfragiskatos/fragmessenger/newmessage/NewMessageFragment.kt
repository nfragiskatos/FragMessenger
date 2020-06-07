package com.nfragiskatos.fragmessenger.newmessage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nfragiskatos.fragmessenger.MainViewModel
import com.nfragiskatos.fragmessenger.R
import com.nfragiskatos.fragmessenger.databinding.FragmentNewMessageBinding

class NewMessageFragment : Fragment() {

    private val TAG = "NewMessageFragment"

    private val viewModel: NewMessageViewModel by lazy {
        ViewModelProvider(this).get(NewMessageViewModel::class.java)
    }

    private lateinit var binding: FragmentNewMessageBinding

    companion object {
        fun newInstance() = NewMessageFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewMessageBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val mainViewModel = activity?.let { ViewModelProvider(it).get(MainViewModel::class.java) }
        mainViewModel?.updateActionBarTitle(getString(R.string.select_user))

        binding.recyclerviewNewMessage.adapter = UserListAdapter()
        viewModel.setData()

        return binding.root
    }
}