package com.nfragiskatos.fragmessenger

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nfragiskatos.fragmessenger.domain.models.User
import com.nfragiskatos.fragmessenger.presentation.ui.chatlog.ChatLogListAdapter
import com.nfragiskatos.fragmessenger.presentation.ui.chatlog.ChatLogMessageItem
import com.nfragiskatos.fragmessenger.presentation.ui.contactlist.UserListAdapter
import com.nfragiskatos.fragmessenger.presentation.ui.latestmessages.LatestMessageItem
import com.nfragiskatos.fragmessenger.presentation.ui.latestmessages.LatestMessagesListAdapter
import com.nfragiskatos.fragmessenger.utility.LoadingStatus


@BindingAdapter("userListData")
fun bindUserRecyclerView(recyclerView: RecyclerView, data: List<User>?) {
    (recyclerView.adapter as UserListAdapter).run {
        submitList(data)
    }
}

@BindingAdapter("messageListData")
fun bindChatMessageRecyclerView(
    recyclerView: RecyclerView,
    data: List<ChatLogMessageItem>?
) {
    (recyclerView.adapter as ChatLogListAdapter).run {
        submitList(data)
        notifyItemInserted(data!!.size)
    }
}

@BindingAdapter("latestMessagesData")
fun bindLatestMessagesRecyclerView(recyclerView: RecyclerView, data: List<LatestMessageItem>?) {
    (recyclerView.adapter as LatestMessagesListAdapter).run {
        submitList(data)
        notifyDataSetChanged()
    }
}

@BindingAdapter("loadingStatus")
fun bindLogInStatus(progressBar: ProgressBar, status: LoadingStatus?) {
    progressBar.visibility = when (status) {
        LoadingStatus.LOADING -> {
            View.VISIBLE
        }

        else -> {
            View.GONE
        }
    }
}