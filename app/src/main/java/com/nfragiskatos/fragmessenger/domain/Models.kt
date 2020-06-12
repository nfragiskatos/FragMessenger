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

sealed class ChatMessageItem {

    data class FromMessage(val messageContent: String) {

    }

    data class ToMessage(val messageContent: String) {

    }
}