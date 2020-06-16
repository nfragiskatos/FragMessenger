package com.nfragiskatos.fragmessenger.chatlog

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
import com.nfragiskatos.fragmessenger.domain.User

class ChatLogViewModel(_contact: User) : ViewModel() {

    val contact = _contact

    val newMessage = MutableLiveData<String>()

    private val _chatMessages = MutableLiveData<MutableList<ChatMessageItem>>(mutableListOf())
    val chatMessages: LiveData<MutableList<ChatMessageItem>>
        get() = _chatMessages

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

    fun sendMessage() {
        val ref = Firebase.database.getReference("/messages").push()
        val fromId = Firebase.auth.uid
        val toId = contact.uid

        if (fromId == null) return

        newMessage.value?.let {
            val message =
                ChatMessage(ref.key!!, it, fromId, toId, System.currentTimeMillis() / 1000)
            ref.setValue(message)
                .addOnSuccessListener {
                    log("Saved our chat message: ${ref.key}")
                }
        }
        newMessage.value = ""
    }

    fun listenForMessages() {
        val ref = Firebase.database.getReference("/messages")
        ref.addChildEventListener(object : ChildEventListener {
            var count = 0;
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
                val message = p0.getValue(ChatMessage::class.java)
                message?.let { msg ->
                    log(msg.text)
                    if (message.fromId == Firebase.auth.uid) {
                        _chatMessages.value?.add(ChatMessageItem.FromMessage(msg))
                    } else {
                        _chatMessages.value?.add(ChatMessageItem.ToMessage(msg, contact))
                    }
                    _chatMessages.value = _chatMessages.value
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("Not yet implemented")
            }
        })
    }
}