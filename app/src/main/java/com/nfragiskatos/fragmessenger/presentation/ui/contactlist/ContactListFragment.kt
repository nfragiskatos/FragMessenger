package com.nfragiskatos.fragmessenger.presentation.ui.contactlist

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
import androidx.recyclerview.widget.DividerItemDecoration
import com.nfragiskatos.fragmessenger.MainViewModel
import com.nfragiskatos.fragmessenger.R
import com.nfragiskatos.fragmessenger.databinding.FragmentContactListBinding

class ContactListFragment : Fragment() {

    private val TAG = "NewMessageFragment"

    private val viewModel: ContactListViewModel by lazy {
        ViewModelProvider(this).get(ContactListViewModel::class.java)
    }

    private lateinit var binding: FragmentContactListBinding

    companion object {
        fun newInstance() = ContactListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val mainViewModel = activity?.let { ViewModelProvider(it).get(MainViewModel::class.java) }
        mainViewModel?.updateActionBarTitle(getString(R.string.select_user))

        binding.recyclerviewNewMessage.adapter =
            UserListAdapter(UserListAdapter.OnClickListenerUserList { user ->
                viewModel.displayChatLogScreen(user)
            })

        binding.recyclerviewNewMessage.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )

        viewModel.logMessage.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, it)
        })

        viewModel.notification.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT)
                .show()
        })

        viewModel.navigateToChatLogScreen.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                this.findNavController().navigate(
                    ContactListFragmentDirections.actionContactListFragmentToChatLogFragment(
                        user
                    )
                )
                viewModel.displayChatLogScreenCompleted()
            }
        })

        viewModel.fetchUsers()

        return binding.root
    }
}