package com.nfragiskatos.fragmessenger.register

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nfragiskatos.fragmessenger.domain.User
import com.nfragiskatos.fragmessenger.login.LogInStatus
import com.nfragiskatos.fragmessenger.repository.FirebaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

enum class RegisterStatus { LOADING, ERROR, DONE }

class RegisterViewModel : ViewModel() {

    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val selectedPhotoUri = MutableLiveData<Uri>()

    private val _notification = MutableLiveData<String>()
    val notification: LiveData<String>
        get() = _notification

    private val _logMessage = MutableLiveData<String>()
    val logMessage: LiveData<String>
        get() = _logMessage

    private val _navigateToLogInScreen = MutableLiveData<Boolean>()
    val navigateToLogInScreen: LiveData<Boolean>
        get() = _navigateToLogInScreen

    private val _navigateToLatestMessagesScreen = MutableLiveData<Boolean>()
    val navigateToLatestMessagesScreen: LiveData<Boolean>
        get() = _navigateToLatestMessagesScreen

    private val _status = MutableLiveData<LogInStatus>()
    val status: LiveData<LogInStatus>
        get() = _status

    private val repo = FirebaseRepository()

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun displayLogInScreen() {
        _navigateToLogInScreen.value = true
    }

    fun displayLogInScreenComplete() {
        _navigateToLogInScreen.value = false
    }

    fun displayLatestMessagesScreen() {
        _navigateToLatestMessagesScreen.value = true
    }

    fun displayLatestMessagesScreenComplete() {
        _navigateToLatestMessagesScreen.value = false
    }

    fun performRegistration() {
        if (email.value == null || password.value == null) {
            _notification.value = "Please enter text in email and password."
            return
        }

        coroutineScope.launch {
            _status.value = LogInStatus.LOADING
            val result = repo.performRegistration(email.value!!, password.value!!)
            if (result != null) {
                _logMessage.value =
                    "Successfully created user with\nuid: ${result.user?.uid}\nemail: ${result.user?.email}"
                uploadImageToFirebaseStorage()
            }
        }
    }

    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri.value == null) {
            return
        }

        coroutineScope.launch {
            var filename = UUID.randomUUID().toString()
            val uri =
                repo.uploadImageToStorage(selectedPhotoUri.value!!, filename, "/images/")
            if (uri != null) {
                saveUserToFirebaseDatabase(uri.toString())
            }
        }

    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        coroutineScope.launch {
            val uid = Firebase.auth.uid ?: ""
            val user = User(
                uid,
                username.value ?: "",
                profileImageUrl
            )
            repo.saveUserToDatabase(user, uid, "/users/")
            displayLatestMessagesScreen()
        }
    }
}

