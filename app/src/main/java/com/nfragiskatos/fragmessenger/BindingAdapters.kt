package com.nfragiskatos.fragmessenger

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nfragiskatos.fragmessenger.chatlog.ChatLogMessageItem
import com.nfragiskatos.fragmessenger.chatlog.ChatLogListAdapter
import com.nfragiskatos.fragmessenger.domain.User
import com.nfragiskatos.fragmessenger.contactlist.UserListAdapter


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