package com.nfragiskatos.fragmessenger.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LogInViewModel : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _navigateToLatestMessagesScreen = MutableLiveData<Boolean>()
    val navigateToLatestMessagesScreen: LiveData<Boolean>
        get() = _navigateToLatestMessagesScreen

    fun displayLatestMessagesScreen() {
        _navigateToLatestMessagesScreen.value = true
    }

    fun displayLatestMessagesScreenComplete() {
        _navigateToLatestMessagesScreen.value = false
    }

}
