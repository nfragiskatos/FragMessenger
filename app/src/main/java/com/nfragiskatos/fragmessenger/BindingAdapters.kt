package com.nfragiskatos.fragmessenger

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nfragiskatos.fragmessenger.chatlog.MessageListAdapter
import com.nfragiskatos.fragmessenger.domain.ChatMessageItem
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
    data: List<ChatMessageItem.FromMessage>?
) {
    val adapter = recyclerView.adapter as MessageListAdapter
    adapter.submitList(data)
}