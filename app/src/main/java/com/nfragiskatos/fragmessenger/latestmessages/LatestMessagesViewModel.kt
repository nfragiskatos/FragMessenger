package com.nfragiskatos.fragmessenger.latestmessages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LatestMessagesViewModel : ViewModel() {

    private val _navigateToRegisterScreen = MutableLiveData<Boolean>()
    val navigateToRegisterScreen: LiveData<Boolean>
        get() = _navigateToRegisterScreen

    fun displayRegisterScreen() {
        _navigateToRegisterScreen.value = true
    }

    fun displayRegisterScreenComplete() {
        _navigateToRegisterScreen.value = false
    }
}