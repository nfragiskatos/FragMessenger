package com.nfragiskatos.fragmessenger.register

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nfragiskatos.fragmessenger.domain.User
import com.nfragiskatos.fragmessenger.repository.FirebaseRepository
import com.nfragiskatos.fragmessenger.utility.LoadingStatus
import kotlinx.coroutines.launch
import java.util.UUID

private const val TAG = "RegisterViewModel"

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

    private val _status = MutableLiveData<LoadingStatus>()
    val status: LiveData<LoadingStatus>
        get() = _status

    fun displayLogInScreen() {
        _navigateToLogInScreen.value = true
    }

    fun displayLogInScreenComplete() {
        _navigateToLogInScreen.value = false
    }

    private fun displayLatestMessagesScreen() {
        _navigateToLatestMessagesScreen.value = true
    }

    fun displayLatestMessagesScreenComplete() {
        _navigateToLatestMessagesScreen.value = false
    }

    fun performRegistration() {

        // TODO: Also need to first check Firebase if the username is already taken

        if (isIncompleteForm()) {
            _notification.value = "Please enter a username, email, and password."
            return
        }

        viewModelScope.launch {
            _status.value = LoadingStatus.LOADING

            try {
                val result = FirebaseRepository.performRegistration(email.value!!, password.value!!)
                if (result != null) {
                    _logMessage.value =
                        "Successfully created user with\nuid: ${result.user?.uid}\nemail: ${result.user?.email}"
                    val profileImageUri = uploadProfileImageToFirebaseStorage()
                    Firebase.auth.uid?.also { uid ->
                        saveUserToFirebaseDatabase(uid, username.value!!, profileImageUri)
                    }
                } else {
                    _status.value = LoadingStatus.DONE
                }

            } catch (e: FirebaseAuthWeakPasswordException) {
                setError(e.message ?: "Password must be at least 6 characters")
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                setError(e.message ?: "Password must be at least 6 characters")
            } catch (e: FirebaseAuthUserCollisionException) {
                setError(e.message ?: "Email already in use")
            }
        }
    }

    private fun isIncompleteForm(): Boolean {
        return email.value.isNullOrBlank() || password.value.isNullOrBlank() || username.value.isNullOrBlank()
    }

    private fun setError(message: String) {
        Log.d(TAG, message)
        _status.value = LoadingStatus.ERROR
        _notification.value = message
    }

    private suspend fun uploadProfileImageToFirebaseStorage(): Uri? {
        val filename = UUID.randomUUID().toString()
        return if (selectedPhotoUri.value == null) null else
            FirebaseRepository.uploadImageToStorage(selectedPhotoUri.value!!, filename, "/images/")
    }

    private suspend fun saveUserToFirebaseDatabase(
        uid: String,
        username: String,
        profileImageUri: Uri?
    ) {
        val user = User(
            uid,
            username,
            profileImageUri?.toString()
        )
        FirebaseRepository.saveUserToDatabase(user, uid, "/users/")
        _status.value = LoadingStatus.DONE
        displayLatestMessagesScreen()
    }
}

