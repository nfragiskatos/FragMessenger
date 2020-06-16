package com.nfragiskatos.fragmessenger.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * Domain object to represent our user on the app
 */
@Parcelize
data class User(val uid: String, val username: String, val profileImageUrl: String) : Parcelable {
    constructor() : this("", "", "")
}

data class ChatMessage(
    val id: String,
    val text: String,
    val fromId: String,
    val toId: String,
    val timestamp: Long
) {
    constructor() : this("", "", "", "", -1L)
}

