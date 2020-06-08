package com.nfragiskatos.fragmessenger.register

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

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

        email.value?.let { email ->
            password.value?.let { password ->
                Firebase.auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        onCompletedRegistration(it)
                    }
                    .addOnFailureListener {
                        onFailedRegistration(it)
                    }
            }
        }
    }

    private fun onCompletedRegistration(result: AuthResult) {
        _logMessage.value =
            "Successfully created user with\nuid: ${result.user?.uid}\nemail: ${result.user?.email}"
        uploadImageToFirebaseStorage()
    }

    private fun onFailedRegistration(result: Exception) {
        _logMessage.value = "Failed to create user: ${result.message}"
        _notification.value = "Failed to create user: ${result.message}"
    }

    private fun uploadImageToFirebaseStorage() {

        if (selectedPhotoUri.value == null) {
            return
        }

        var filename = UUID.randomUUID().toString()
        val ref = Firebase.storage.getReference("/images/$filename")

        ref.putFile(selectedPhotoUri.value!!)
            .addOnSuccessListener { it ->
                _logMessage.value = "Successfully uploaded image: ${it.metadata?.path}"

                ref.downloadUrl.addOnSuccessListener {
                    _logMessage.value = "File Location: $it"
                    saveUserToFirebaseDatabase(it.toString())
                }
            }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = Firebase.auth.uid ?: ""
        val ref = Firebase.database.getReference("/users/$uid")

        val user = User(uid, username.value ?: "", profileImageUrl)
        ref.setValue(user)
            .addOnSuccessListener {
                _logMessage.value = "Finally saved user to Firebase database"
                displayLatestMessagesScreen()
            }
            .addOnFailureListener {
                _logMessage.value = "Failed: $it"
            }
    }
}

class User(val uid: String, val username: String, val profileImageUrl: String)
