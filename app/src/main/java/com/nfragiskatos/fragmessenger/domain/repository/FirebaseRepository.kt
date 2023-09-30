package com.nfragiskatos.fragmessenger.domain.repository

import android.net.Uri
import com.google.firebase.auth.AuthResult
import com.nfragiskatos.fragmessenger.domain.models.RegistrationResult
import com.nfragiskatos.fragmessenger.domain.models.User
import com.nfragiskatos.fragmessenger.utility.Result

interface FirebaseRepository {
    suspend fun performLogIn(email: String, password: String): AuthResult?

//    suspend fun performRegistration(email: String, password: String): AuthResult?

    suspend fun performRegistration(email: String, password: String): Result<RegistrationResult>

    suspend fun uploadImageToStorage(uri: Uri, fileName: String, nodePath: String): Uri?

    suspend fun saveUserToDatabase(user: User, uid: String, nodePath: String)
    fun validatePath(nodePath: String): String
}