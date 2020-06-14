package com.nfragiskatos.fragmessenger.chatlog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nfragiskatos.fragmessenger.domain.ChatMessage
import com.nfragiskatos.fragmessenger.domain.User

class ChatLogViewModel(_contact: User) : ViewModel() {

    val contact = _contact

    val newMessage = MutableLiveData<String>()

    private val _chatMessages = MutableLiveData<List<ChatMessageItem>>()
    val chatMessages: LiveData<List<ChatMessageItem>>
        get() = _chatMessages

    private val _notification = MutableLiveData<String>()
    val notification: LiveData<String>
        get() = _notification

    private val _logMessage = MutableLiveData<String>()
    val logMessage: LiveData<String>
        get() = _logMessage

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
                    _logMessage.value = "Saved our chat message: ${ref.key}"
                }
        }
    }

    fun getData() {
        val data = ArrayList<ChatMessageItem>()
        data.add(
            ChatMessageItem.FromMessage(
                ChatMessage(
                    "testUid",
                    "This is short.",
                    "testFrom",
                    "testTo",
                    100L
                )
            )
        )
        data.add(
            ChatMessageItem.ToMessage(
                ChatMessage(
                    "testUid",
                    "This is a really long message that will wrap into multiple lines and keep on going maybe. I don't know.",
                    "testFrom",
                    "testTo",
                    100L
                )
            )
        )
        data.add(
            ChatMessageItem.FromMessage(
                ChatMessage(
                    "testUid",
                    "This is short.",
                    "testFrom",
                    "testTo",
                    100L
                )
            )
        )
        data.add(
            ChatMessageItem.ToMessage(
                ChatMessage(
                    "testUid",
                    "This is a really long message that will wrap into multiple lines and keep on going maybe. I don't know.",
                    "testFrom",
                    "testTo",
                    100L
                )
            )
        )
        data.add(
            ChatMessageItem.FromMessage(
                ChatMessage(
                    "testUid",
                    "This is short.",
                    "testFrom",
                    "testTo",
                    100L
                )
            )
        )
        data.add(
            ChatMessageItem.ToMessage(
                ChatMessage(
                    "testUid",
                    "This is a really long message that will wrap into multiple lines and keep on going maybe. I don't know.",
                    "testFrom",
                    "testTo",
                    100L
                )
            )
        )
        data.add(
            ChatMessageItem.FromMessage(
                ChatMessage(
                    "testUid",
                    "This is short.",
                    "testFrom",
                    "testTo",
                    100L
                )
            )
        )
        data.add(
            ChatMessageItem.ToMessage(
                ChatMessage(
                    "testUid",
                    "This is a really long message that will wrap into multiple lines and keep on going maybe. I don't know.",
                    "testFrom",
                    "testTo",
                    100L
                )
            )
        )

        _chatMessages.value = data
    }
}