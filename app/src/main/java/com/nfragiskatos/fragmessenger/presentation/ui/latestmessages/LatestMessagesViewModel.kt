package com.nfragiskatos.fragmessenger.presentation.ui.latestmessages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nfragiskatos.fragmessenger.domain.models.ChatMessage
import com.nfragiskatos.fragmessenger.domain.models.User

class LatestMessagesViewModel : ViewModel() {

    private val _navigateToRegisterScreen = MutableLiveData<Boolean>()
    val navigateToRegisterScreen: LiveData<Boolean>
        get() = _navigateToRegisterScreen

    private val _latestMessages = MutableLiveData<MutableList<LatestMessageItem>>(mutableListOf())
    val latestMessages: LiveData<MutableList<LatestMessageItem>>
        get() = _latestMessages

    private val _navigateToChatLogScreen = MutableLiveData<User>()
    val navigateToChatLogScreen: LiveData<User>
        get() = _navigateToChatLogScreen

    fun displayRegisterScreen() {
        _navigateToRegisterScreen.value = true
    }

    fun displayRegisterScreenComplete() {
        _navigateToRegisterScreen.value = false
    }

    fun displayChatLogScreen(user: User) {
        _navigateToChatLogScreen.value = user
    }

    fun displayChatLogScreenCompleted() {
        _navigateToChatLogScreen.value = null
    }

    private val _navigateToNewMessageScreen = MutableLiveData<Boolean>()
    val navigateToNewMessageScreen: LiveData<Boolean>
        get() = _navigateToNewMessageScreen

    fun displayNewMessageScreen() {
        _navigateToNewMessageScreen.value = true
    }

    fun displayNewMessageScreenComplete() {
        _navigateToNewMessageScreen.value = false
    }

    private val _notification = MutableLiveData<String>()
    val notification: LiveData<String>
        get() = _notification

    private val _logMessage = MutableLiveData<String>()
    val logMessage: LiveData<String>
        get() = _logMessage

    private val latestMessagesMap = HashMap<String, LatestMessageItem>()

    private fun log(message: String) {
        _logMessage.value = message
    }

    private fun notify(message: String) {
        _notification.value = message
    }

    fun refreshLatestMessageList() {
        latestMessages.value?.let { list ->
            list.clear()
            latestMessagesMap.values.forEach {
                list.add(it)
            }
        }
        _latestMessages.value = latestMessages.value
    }

    fun listenForLatestMessages() {
        val fromId = Firebase.auth.uid
        val ref = Firebase.database.getReference("/latest-messages/$fromId")

        ref.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val message = p0.getValue(ChatMessage::class.java) ?: return
                log(message.text)
                val key = p0.key!!
                val chatPartnerId =
                    if (message.fromId == Firebase.auth.uid) message.toId else message.fromId

                val ref = Firebase.database.getReference("/users/$chatPartnerId")
                ref.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val user = p0.getValue(User::class.java) ?: return
                        log("User: ${user?.username}, Message: ${message.text}")
                        latestMessagesMap[key] = LatestMessageItem(message, user)
                        refreshLatestMessageList()
                    }
                })
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val message = p0.getValue(ChatMessage::class.java) ?: return
                log(message.text)
                val key = p0.key!!
                val chatPartnerId =
                    if (message.fromId == Firebase.auth.uid) message.toId else message.fromId

                val ref = Firebase.database.getReference("/users/$chatPartnerId")
                ref.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val user = p0.getValue(User::class.java) ?: return
                        log("User: ${user?.username}, Message: ${message.text}")
                        latestMessagesMap[key] = LatestMessageItem(message, user)
                        refreshLatestMessageList()
                    }
                })
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("Not yet implemented")
            }

        })
    }
}