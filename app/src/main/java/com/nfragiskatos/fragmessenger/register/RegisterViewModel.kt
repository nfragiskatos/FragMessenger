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
import com.nfragiskatos.fragmessenger.login.LogInStatus
import com.nfragiskatos.fragmessenger.repository.FirebaseRepository
import kotlinx.coroutines.launch
import java.util.*

enum class RegisterStatus { LOADING, ERROR, DONE }
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

    private val _status = MutableLiveData<LogInStatus>()
    val status: LiveData<LogInStatus>
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
        if (isIncompleteForm()) {
            _notification.value = "Please enter username, email, and password."
            return
        }

        viewModelScope.launch {
            _status.value = LogInStatus.LOADING

            try {
                val result = FirebaseRepository.performRegistration(email.value!!, password.value!!)
                if (result != null) {
                    _logMessage.value =
                        "Successfully created user with\nuid: ${result.user?.uid}\nemail: ${result.user?.email}"
                    uploadImageToFirebaseStorage()
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

    private fun isIncompleteForm() : Boolean {
        return email.value == null || email.value!!.isBlank() || password.value == null || password.value!!.isBlank() || username.value == null || username.value!!.isBlank()
    }

    private fun setError(message:String) {
        Log.d(TAG, message)
        _status.value = LogInStatus.ERROR
        _notification.value = message
    }

    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri.value == null) {
            return
        }

        viewModelScope.launch {
            val filename = UUID.randomUUID().toString()
            val uri =
                FirebaseRepository.uploadImageToStorage(selectedPhotoUri.value!!, filename, "/images/")
            if (uri != null) {
                saveUserToFirebaseDatabase(uri.toString())
            }
        }

    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        viewModelScope.launch {
            val uid = Firebase.auth.uid ?: ""
            val user = User(
                uid,
                username.value ?: "",
                profileImageUrl
            )
            FirebaseRepository.saveUserToDatabase(user, uid, "/users/")
            _status.value = LogInStatus.DONE
            displayLatestMessagesScreen()
        }
    }
}

