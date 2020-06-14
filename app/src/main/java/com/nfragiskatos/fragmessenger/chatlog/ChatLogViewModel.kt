package com.nfragiskatos.fragmessenger.chatlog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
        _notification.value = "New message to send: ${newMessage.value}"
    }

    fun getData() {
        val data = ArrayList<ChatMessageItem>()
        data.add(ChatMessageItem.FromMessage(ChatMessage("This is short.")))
        data.add(ChatMessageItem.ToMessage(ChatMessage("This is a really long message that will wrap into multiple lines and keep on going maybe. I don't know.")))
        data.add(ChatMessageItem.FromMessage(ChatMessage("This is a really long message that will wrap into multiple lines and keep on going maybe. I don't know.")))
        data.add(ChatMessageItem.ToMessage(ChatMessage("This is a really long message that will wrap into multiple lines and keep on going maybe. I don't know.")))
        data.add(ChatMessageItem.FromMessage(ChatMessage("This is a really long message that will wrap into multiple lines and keep on going maybe. I don't know.")))
        data.add(ChatMessageItem.ToMessage(ChatMessage("This is a really long message that will wrap into multiple lines and keep on going maybe. I don't know.")))
        data.add(ChatMessageItem.FromMessage(ChatMessage("This is a really long message that will wrap into multiple lines and keep on going maybe. I don't know.")))
        data.add(ChatMessageItem.FromMessage(ChatMessage("This is a really long message that will wrap into multiple lines and keep on going maybe. I don't know.")))
        data.add(ChatMessageItem.ToMessage(ChatMessage("This is a really long message that will wrap into multiple lines and keep on going maybe. I don't know.")))
        data.add(ChatMessageItem.FromMessage(ChatMessage("This is a really long message that will wrap into multiple lines and keep on going maybe. I don't know.")))
        _chatMessages.value = data
    }
}