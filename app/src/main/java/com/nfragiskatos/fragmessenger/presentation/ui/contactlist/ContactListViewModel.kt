package com.nfragiskatos.fragmessenger.presentation.ui.contactlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nfragiskatos.fragmessenger.domain.models.User

class ContactListViewModel : ViewModel() {

    private val _navigateToChatLogScreen = MutableLiveData<User>()
    val navigateToChatLogScreen: LiveData<User>
        get() = _navigateToChatLogScreen

    fun displayChatLogScreen(user: User) {
        _navigateToChatLogScreen.value = user
    }

    fun displayChatLogScreenCompleted() {
        _navigateToChatLogScreen.value = null
    }

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    private val _notification = MutableLiveData<String>()
    val notification: LiveData<String>
        get() = _notification

    private val _logMessage = MutableLiveData<String>()
    val logMessage: LiveData<String>
        get() = _logMessage

    fun fetchUsers() {
        val ref = Firebase.database.getReference("/users").apply {
            addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    _logMessage.value = "User fetch cancelled"
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val map = p0.children.map {
                        it.getValue(User::class.java)
                    }
                    _users.value = map as List<User>
                }
            })
        }
    }
}