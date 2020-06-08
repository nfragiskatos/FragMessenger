package com.nfragiskatos.fragmessenger.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInViewModel : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _navigateToLatestMessagesScreen = MutableLiveData<Boolean>()
    val navigateToLatestMessagesScreen: LiveData<Boolean>
        get() = _navigateToLatestMessagesScreen

    private val _notification = MutableLiveData<String>()
    val notification: LiveData<String>
        get() = _notification

    private val _logMessage = MutableLiveData<String>()
    val logMessage: LiveData<String>
        get() = _logMessage

    fun displayLatestMessagesScreen() {
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

        email.value?.let { email ->
            password.value?.let { password ->
                Firebase.auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        onCompletedLogIn(it)
                    }.addOnFailureListener {
                        onFailedLogIn(it)
                    }
            }
        }
    }

    private fun onCompletedLogIn(result: Task<AuthResult>) {
        if (!result.isSuccessful) {
            _logMessage.value = "Completed with failure: ${result.exception}"
            return
        }

        _logMessage.value =
            "Successfully logged in user with\nuid: ${result.result?.user?.uid}\nemail: ${result.result?.user?.email}"
        displayLatestMessagesScreen()
    }

    private fun onFailedLogIn(result: Exception) {
        _notification.value = "Failed to log in user: ${result.message}"
        _logMessage.value = "Failed to log in user: ${result.message}"
    }

}
