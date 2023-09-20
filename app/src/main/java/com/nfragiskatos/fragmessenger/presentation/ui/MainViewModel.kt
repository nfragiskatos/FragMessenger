package com.nfragiskatos.fragmessenger.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _actionBarTitle = MutableLiveData<String>()
    val actionBarTitle: LiveData<String>
        get() = _actionBarTitle

    fun updateActionBarTitle(title: String) = _actionBarTitle.postValue(title)
}