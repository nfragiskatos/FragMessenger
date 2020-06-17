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
import com.nfragiskatos.fragmessenger.latestmessages.LatestMessagesFragment

class ChatLogViewModel(_contact: User) : ViewModel() {

    val contact = _contact
    val newMessage = MutableLiveData<String>()
    private val _messageAdded = MutableLiveData<Boolean>()
    val messageAdded: LiveData<Boolean>
        get() = _messageAdded

    fun messageAddedComplete() {
        _messageAdded.value = false
    }

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
        val fromId = Firebase.auth.uid
        val toId = contact.uid
        val fromRef = Firebase.database.getReference("/user-messages/$fromId/$toId").push()
        val toRef = Firebase.database.getReference("/user-messages/$toId/$fromId").push()

        if (fromId == null) return

        newMessage.value?.let {
            val message =
                ChatMessage(fromRef.key!!, it, fromId, toId, System.currentTimeMillis() / 1000)
            fromRef.setValue(message)
                .addOnSuccessListener {
                    log("Saved our chat message: ${fromRef.key}")
                }
            toRef.setValue(message)
                .addOnSuccessListener {
                    log("Saved to reference chat message: ${toRef.key}")
                }
        }
        newMessage.value = ""
        _messageAdded.value = true
    }

    fun listenForMessages() {
        val fromId = Firebase.auth.uid
        val toId = contact.uid
        val ref = Firebase.database.getReference("/user-messages/$fromId/$toId")
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
                        val currentUser = LatestMessagesFragment.currentUser ?: return
                        _chatMessages.value?.add(ChatMessageItem.FromMessage(msg, currentUser))
                    } else {
                        _chatMessages.value?.add(ChatMessageItem.ToMessage(msg, contact))
                    }
                    _chatMessages.value = _chatMessages.value
                    _messageAdded.value = true
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("Not yet implemented")
            }
        })
    }
}