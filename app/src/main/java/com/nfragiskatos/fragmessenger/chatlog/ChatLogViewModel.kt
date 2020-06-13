package com.nfragiskatos.fragmessenger.chatlog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nfragiskatos.fragmessenger.domain.ChatMessage

class ChatLogViewModel : ViewModel() {

    private val _chatMessages = MutableLiveData<List<ChatMessageItem>>()
    val chatMessages: LiveData<List<ChatMessageItem>>
        get() = _chatMessages

    fun getData() {
        val data = ArrayList<ChatMessageItem>()
        data.add(ChatMessageItem.FromMessage(ChatMessage("Content 1")))
        data.add(ChatMessageItem.ToMessage(ChatMessage("Content 2")))
        data.add(ChatMessageItem.FromMessage(ChatMessage("Content 3")))
        data.add(ChatMessageItem.ToMessage(ChatMessage("Content 4")))
        data.add(ChatMessageItem.FromMessage(ChatMessage("Content 5")))
        data.add(ChatMessageItem.ToMessage(ChatMessage("Content 6")))
        data.add(ChatMessageItem.FromMessage(ChatMessage("Content 7")))
        data.add(ChatMessageItem.FromMessage(ChatMessage("Content 8")))
        data.add(ChatMessageItem.ToMessage(ChatMessage("Content 9")))
        data.add(ChatMessageItem.FromMessage(ChatMessage("Content 10")))
        _chatMessages.value = data
    }
}