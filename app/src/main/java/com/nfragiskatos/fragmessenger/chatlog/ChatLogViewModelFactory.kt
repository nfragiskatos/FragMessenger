package com.nfragiskatos.fragmessenger.chatlog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nfragiskatos.fragmessenger.domain.models.User

class ChatLogViewModelFactory(private val contact: User) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatLogViewModel::class.java)) {
            return ChatLogViewModel(contact) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}