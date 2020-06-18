package com.nfragiskatos.fragmessenger

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nfragiskatos.fragmessenger.chatlog.ChatMessageItem
import com.nfragiskatos.fragmessenger.chatlog.ChatLogListAdapter
import com.nfragiskatos.fragmessenger.domain.User
import com.nfragiskatos.fragmessenger.newmessage.UserListAdapter


@BindingAdapter("userListData")
fun bindUserRecyclerView(recyclerView: RecyclerView, data: List<User>?) {
    val adapter = recyclerView.adapter as UserListAdapter
    adapter.submitList(data)
}

@BindingAdapter("messageListData")
fun bindChatMessageRecyclerView(
    recyclerView: RecyclerView,
    data: List<ChatMessageItem>?
) {
    val adapter = recyclerView.adapter as ChatLogListAdapter
    adapter.submitList(data)
    adapter.notifyDataSetChanged()
}