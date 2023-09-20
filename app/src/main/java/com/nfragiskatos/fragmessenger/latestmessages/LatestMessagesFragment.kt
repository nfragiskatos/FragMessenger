package com.nfragiskatos.fragmessenger.latestmessages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nfragiskatos.fragmessenger.MainViewModel
import com.nfragiskatos.fragmessenger.R
import com.nfragiskatos.fragmessenger.databinding.FragmentLatestMessagesBinding
import com.nfragiskatos.fragmessenger.domain.models.User

class LatestMessagesFragment : Fragment() {

    private val TAG = "LatestMessagesFragment"

    private val viewModel: LatestMessagesViewModel by lazy {
        ViewModelProvider(this).get(LatestMessagesViewModel::class.java)
    }

    private lateinit var binding: FragmentLatestMessagesBinding

    companion object {
        fun newInstance() = LatestMessagesFragment()
        var currentUser: User? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLatestMessagesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        Firebase.auth.addAuthStateListener {
            if (it.currentUser == null) {
                viewModel.displayRegisterScreen()
            } else {
                Log.d(TAG, "${it.currentUser?.email} is already signed in")
            }
        }

        fetchCurrentUser()

        viewModel.navigateToRegisterScreen.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                Log.d(TAG,
                    findNavController().currentDestination?.toString()
                        ?: "Navigating to register screen"
                )
                if (findNavController().currentDestination?.id == R.id.latestMessagesFragment) {
                    Log.d(TAG, "LOG OUT SUCCESS")
                    findNavController().navigate(LatestMessagesFragmentDirections.actionLatestMessagesFragmentToRegisterFragment())
                    viewModel.displayRegisterScreenComplete()
                } else {
                    Log.d(TAG, "FAILED LOGOUT")
                }
                viewModel.displayRegisterScreenComplete()
            }
        })

        viewModel.navigateToNewMessageScreen.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                findNavController().navigate(LatestMessagesFragmentDirections.actionLatestMessagesFragmentToContactListFragment())
                viewModel.displayNewMessageScreenComplete()
            }
        })

        setHasOptionsMenu(true)

        binding.recyclerviewMessagesLatestMessages.adapter =
            LatestMessagesListAdapter(LatestMessagesListAdapter.OnClickListenerLatestMessages {
                viewModel.displayChatLogScreen(it.user)
            })

        binding.recyclerviewMessagesLatestMessages.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        viewModel.notification.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT)
                .show()
        })

        viewModel.logMessage.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, it)
        })

        viewModel.navigateToChatLogScreen.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                this.findNavController().navigate(
                    LatestMessagesFragmentDirections.actionLatestMessagesFragmentToChatLogFragment(
                        user
                    )
                )
                viewModel.displayChatLogScreenCompleted()
            }
        })

        viewModel.listenForLatestMessages()

        initActionBarTitle()
        return binding.root
    }

    private fun fetchCurrentUser() {
        val uid = Firebase.auth.uid
        val ref = Firebase.database.getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                currentUser = p0.getValue(User::class.java)
                Log.d(TAG, "Current User: ${currentUser?.username}")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_new_message -> {
                Log.d(TAG, "Creating new message...")
                viewModel.displayNewMessageScreen()
            }

            R.id.menu_sign_out -> {
                Log.d(TAG, "Signing out...")
                Firebase.auth.signOut()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initActionBarTitle() {
        val mainViewModel = activity?.let { ViewModelProvider(it).get(MainViewModel::class.java) }
        mainViewModel?.updateActionBarTitle(resources.getString(R.string.app_name))
    }

}