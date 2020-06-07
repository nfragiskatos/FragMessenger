package com.nfragiskatos.fragmessenger.newmessage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewMessageViewModel : ViewModel() {

    private val _users = MutableLiveData<List<String>>()
    val users: LiveData<List<String>>
        get() = _users

    fun setData() {
        var users = ArrayList<String>()
        users.add("Harry Potter")
        users.add("Ron Weasley")
        users.add("Hermione Granger")
        users.add("Rubeus Hagrid")
        users.add("Minverva McGonagall")

        _users.value = users
    }
}