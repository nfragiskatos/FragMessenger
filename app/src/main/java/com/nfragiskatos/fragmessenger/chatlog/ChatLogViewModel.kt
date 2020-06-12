package com.nfragiskatos.fragmessenger.chatlog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nfragiskatos.fragmessenger.domain.ChatMessageItem

class ChatLogViewModel : ViewModel() {

    private val _chatMessages = MutableLiveData<List<ChatMessageItem.FromMessage>>()
    val chatMessages: LiveData<List<ChatMessageItem.FromMessage>>
        get() = _chatMessages

    fun getData() {
        val data = ArrayList<ChatMessageItem.FromMessage>()
        data.add(ChatMessageItem.FromMessage("Content 1"))
        data.add(ChatMessageItem.FromMessage("Content 2"))
        data.add(ChatMessageItem.FromMessage("Content 3"))
        data.add(ChatMessageItem.FromMessage("Content 4"))
        data.add(ChatMessageItem.FromMessage("Content 5"))
        data.add(ChatMessageItem.FromMessage("Content 6"))
        data.add(ChatMessageItem.FromMessage("Content 7"))
        data.add(ChatMessageItem.FromMessage("Content 8"))
        data.add(ChatMessageItem.FromMessage("Content 9"))
        data.add(ChatMessageItem.FromMessage("Content 10"))
        _chatMessages.value = data
    }
}