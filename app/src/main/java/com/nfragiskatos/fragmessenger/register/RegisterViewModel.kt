package com.nfragiskatos.fragmessenger.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class RegisterViewModel : ViewModel() {

    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

}
