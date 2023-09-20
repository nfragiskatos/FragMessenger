package com.nfragiskatos.fragmessenger

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nfragiskatos.fragmessenger.contactlist.UserListAdapter
import com.nfragiskatos.fragmessenger.domain.models.User
import com.nfragiskatos.fragmessenger.latestmessages.LatestMessageItem
import com.nfragiskatos.fragmessenger.latestmessages.LatestMessagesListAdapter
import com.nfragiskatos.fragmessenger.presentation.ui.chatlog.ChatLogListAdapter
import com.nfragiskatos.fragmessenger.presentation.ui.chatlog.ChatLogMessageItem
import com.nfragiskatos.fragmessenger.utility.LoadingStatus


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
    adapter.notifyItemInserted(data!!.size)
}

@BindingAdapter("latestMessagesData")
fun bindLatestMessagesRecyclerView(recyclerView: RecyclerView, data: List<LatestMessageItem>?) {
    val adapter = recyclerView.adapter as LatestMessagesListAdapter
    adapter.submitList(data)
    adapter.notifyDataSetChanged()
}

@BindingAdapter("loadingStatus")
fun bindLogInStatus(progressBar: ProgressBar, status: LoadingStatus?) {
    when (status) {
        LoadingStatus.LOADING -> {
            progressBar.visibility = View.VISIBLE
        }

        LoadingStatus.DONE, LoadingStatus.ERROR -> {
            progressBar.visibility = View.GONE
        }
    }
}