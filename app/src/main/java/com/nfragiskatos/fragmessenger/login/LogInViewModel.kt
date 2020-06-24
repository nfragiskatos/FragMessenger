package com.nfragiskatos.fragmessenger.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nfragiskatos.fragmessenger.repository.FirebaseRepository
import kotlinx.coroutines.*

enum class LogInStatus { LOADING, ERROR, DONE }

class LogInViewModel : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _status = MutableLiveData<LogInStatus>()
    val status: LiveData<LogInStatus>
        get() = _status

    private val _navigateToLatestMessagesScreen = MutableLiveData<Boolean>()
    val navigateToLatestMessagesScreen: LiveData<Boolean>
        get() = _navigateToLatestMessagesScreen

    private val _notification = MutableLiveData<String>()
    val notification: LiveData<String>
        get() = _notification

    private val _logMessage = MutableLiveData<String>()
    val logMessage: LiveData<String>
        get() = _logMessage

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private fun displayLatestMessagesScreen() {
        _navigateToLatestMessagesScreen.value = true
    }

    fun displayLatestMessagesScreenComplete() {
        _navigateToLatestMessagesScreen.value = false
    }

    fun performLogIn() {
        if (email.value == null || password.value == null) {
            _notification.value = "Please enter text in email and password."
            return
        }

        coroutineScope.launch {
            _status.value = LogInStatus.LOADING
            val result = FirebaseRepository.performLogIn(email.value!!, password.value!!)
            if (result != null) {
                _logMessage.value = "${result.user?.email} successfully logged in"
                displayLatestMessagesScreen()
                _status.value = LogInStatus.DONE
            } else {
                _notification.value = "Failed to log in user"
                _logMessage.value = "Failed to log in user"
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}
