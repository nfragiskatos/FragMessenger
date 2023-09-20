package com.nfragiskatos.fragmessenger.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Domain object to represent our user on the app
 */
@Parcelize
data class User(val uid: String, val username: String, val profileImageUrl: String?) : Parcelable {
    constructor() : this("", "", "")
}