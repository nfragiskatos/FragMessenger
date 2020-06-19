package com.nfragiskatos.fragmessenger.latestmessages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nfragiskatos.fragmessenger.domain.ChatMessage

class LatestMessagesViewModel : ViewModel() {

    private val _navigateToRegisterScreen = MutableLiveData<Boolean>()
    val navigateToRegisterScreen: LiveData<Boolean>
        get() = _navigateToRegisterScreen

    private val _latestMessages = MutableLiveData<MutableList<ChatMessage>>(mutableListOf())
    val latestMessages: LiveData<MutableList<ChatMessage>>
        get() = _latestMessages

    fun displayRegisterScreen() {
        _navigateToRegisterScreen.value = true
    }

    fun displayRegisterScreenComplete() {
        _navigateToRegisterScreen.value = false
    }

    private val _navigateToNewMessageScreen = MutableLiveData<Boolean>()
    val navigateToNewMessageScreen: LiveData<Boolean>
        get() = _navigateToNewMessageScreen

    fun displayNewMessageScreen() {
        _navigateToNewMessageScreen.value = true
    }

    fun displayNewMessageScreenComplete() {
        _navigateToNewMessageScreen.value = false
    }

    private val _notification = MutableLiveData<String>()
    val notification: LiveData<String>
        get() = _notification

    private val _logMessage = MutableLiveData<String>()
    val logMessage: LiveData<String>
        get() = _logMessage

    private fun log(message: String) {
        _logMessage.value = message
    }

    private fun notify(message: String) {
        _notification.value = message
    }

    fun listenForLatestMessages() {
        val fromId = Firebase.auth.uid
        val ref = Firebase.database.getReference("/latest-messages/$fromId")

        ref.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val message = p0.getValue(ChatMessage::class.java) ?: return
                log(message.text)
                _latestMessages.value?.add(message)
                _latestMessages.value = _latestMessages.value

            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("Not yet implemented")
            }

        })
    }
}