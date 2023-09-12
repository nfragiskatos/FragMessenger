package com.nfragiskatos.fragmessenger.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.nfragiskatos.fragmessenger.repository.FirebaseRepository
import kotlinx.coroutines.launch

private const val TAG = "LogInViewModel"

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

    private fun displayLatestMessagesScreen() {
        _navigateToLatestMessagesScreen.value = true
    }

    fun displayLatestMessagesScreenComplete() {
        _navigateToLatestMessagesScreen.value = false
    }

    fun performLogIn() {
        if (email.value.isNullOrBlank() || password.value.isNullOrBlank()) {
            _notification.value = "Please enter an email and password"
            return
        }

        viewModelScope.launch {
            _status.value = LogInStatus.LOADING
            try {
                val result = FirebaseRepository.performLogIn(email.value!!, password.value!!)
                if (result != null) {
                    _logMessage.value = "${result.user?.email} successfully logged in"
                    displayLatestMessagesScreen()
                    _status.value = LogInStatus.DONE
                } else {
                    _notification.value = "Failed to log in user"
                    _logMessage.value = "Failed to log in user"
                }
            } catch (e: FirebaseAuthInvalidUserException) {
                setError(e.message ?: "Invalid User")
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                setError(e.message ?: "Invalid Password")
            } catch (e: FirebaseTooManyRequestsException) {
                setError(e.message ?: "Account Blocked - Too many failed login attempts")
            }
        }
    }

    private fun setError(message: String) {
        Log.d(TAG, message)
        _status.value = LogInStatus.ERROR
        _notification.value = message
    }
}
