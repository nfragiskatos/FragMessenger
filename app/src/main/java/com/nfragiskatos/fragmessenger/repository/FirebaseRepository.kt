package com.nfragiskatos.fragmessenger.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseRepository {

    suspend fun performLogIn(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            return@withContext Firebase.auth.currentUser != null
        }
    }
}