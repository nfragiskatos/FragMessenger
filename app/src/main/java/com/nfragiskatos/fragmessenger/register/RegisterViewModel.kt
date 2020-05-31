package com.nfragiskatos.fragmessenger.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class RegisterViewModel : ViewModel() {

    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _navigateToLogInScreen = MutableLiveData<Boolean>()
    val navigateToLogInScreen: LiveData<Boolean>
        get() = _navigateToLogInScreen

    fun displayLogInScreen() {
        _navigateToLogInScreen.value = true
    }

    fun displayLogInScreenComplete() {
        _navigateToLogInScreen.value = false
    }

}
