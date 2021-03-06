package com.nfragiskatos.fragmessenger

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nfragiskatos.fragmessenger.chatlog.ChatLogListAdapter
import com.nfragiskatos.fragmessenger.chatlog.ChatLogMessageItem
import com.nfragiskatos.fragmessenger.contactlist.UserListAdapter
import com.nfragiskatos.fragmessenger.domain.User
import com.nfragiskatos.fragmessenger.latestmessages.LatestMessageItem
import com.nfragiskatos.fragmessenger.latestmessages.LatestMessagesListAdapter
import com.nfragiskatos.fragmessenger.login.LogInStatus


@BindingAdapter("userListData")
fun bindUserRecyclerView(recyclerView: RecyclerView, data: List<User>?) {
    val adapter = recyclerView.adapter as UserListAdapter
    adapter.submitList(data)
}

@BindingAdapter("messageListData")
fun bindChatMessageRecyclerView(
    recyclerView: RecyclerView,
    data: List<ChatLogMessageItem>?
) {
    val adapter = recyclerView.adapter as ChatLogListAdapter
    adapter.submitList(data)
    adapter.notifyDataSetChanged()
}

@BindingAdapter("latestMessagesData")
fun bindLatestMessagesRecyclerView(recyclerView: RecyclerView, data: List<LatestMessageItem>?) {
    val adapter = recyclerView.adapter as LatestMessagesListAdapter
    adapter.submitList(data)
    adapter.notifyDataSetChanged()
}

@BindingAdapter("logInStatus")
fun bindLogInStatus(statusImageView: ImageView, status: LogInStatus?) {
    when (status) {
        LogInStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        LogInStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}