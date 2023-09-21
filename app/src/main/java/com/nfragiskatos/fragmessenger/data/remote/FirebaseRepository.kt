package com.nfragiskatos.fragmessenger.data.remote

import android.net.Uri
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.nfragiskatos.fragmessenger.domain.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

object FirebaseRepository {

    suspend fun performLogIn(email: String, password: String): AuthResult? {
        return Firebase.auth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun performRegistration(email: String, password: String): AuthResult? {
        return Firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }

    suspend fun uploadImageToStorage(uri: Uri, fileName: String, nodePath: String): Uri? {
        return withContext(Dispatchers.IO) {
            val path = validatePath(nodePath)
            val ref = Firebase.storage.getReference(path + fileName)
            ref.putFile(uri).await()
            return@withContext ref.downloadUrl.await()
        }
    }

    suspend fun saveUserToDatabase(user: User, uid: String, nodePath: String) {
        withContext(Dispatchers.IO) {
            val path = validatePath(nodePath)
            val ref = Firebase.database.getReference(path + uid)
            ref.setValue(user).await()
        }
    }

    private fun validatePath(nodePath: String): String {
        return if (nodePath[nodePath.lastIndex] == '/') nodePath else "$nodePath/"
    }
}