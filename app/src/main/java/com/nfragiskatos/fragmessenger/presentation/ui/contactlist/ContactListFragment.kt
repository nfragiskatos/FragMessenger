package com.nfragiskatos.fragmessenger.presentation.ui.contactlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.nfragiskatos.fragmessenger.R
import com.nfragiskatos.fragmessenger.databinding.FragmentContactListBinding
import com.nfragiskatos.fragmessenger.presentation.ui.MainViewModel

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
    ): View {
        binding = FragmentContactListBinding.inflate(inflater).also { binding ->
            binding.lifecycleOwner = this
            binding.viewModel = viewModel
            binding.recyclerviewNewMessage.adapter =
                UserListAdapter(UserListAdapter.OnClickListenerUserList { user ->
                    viewModel.displayChatLogScreen(user)
                })

            binding.recyclerviewNewMessage.addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }
        initObservers()
        viewModel.fetchUsers()
        initActionBarTitle()
        return binding.root
    }

    private fun initObservers() {
        viewModel.logMessage.observe(viewLifecycleOwner) {
            Log.d(TAG, it)
        }

        viewModel.notification.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT)
                .show()
        }

        viewModel.navigateToChatLogScreen.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                this.findNavController().navigate(
                    ContactListFragmentDirections.actionContactListFragmentToChatLogFragment(
                        user
                    )
                )
                viewModel.displayChatLogScreenCompleted()
            }
        }
    }

    private fun initActionBarTitle() {
        val mainViewModel = activity?.let { ViewModelProvider(it).get(MainViewModel::class.java) }
        mainViewModel?.updateActionBarTitle(getString(R.string.select_user))
    }
}