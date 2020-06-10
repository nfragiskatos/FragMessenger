package com.nfragiskatos.fragmessenger.domain


/**
 * Domain object to represent our user on the app
 */
data class User(val uid: String, val username: String, val profileImageUrl: String) {
    constructor() : this("", "", "")
}