package com.nfragiskatos.fragmessenger.chatlog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nfragiskatos.fragmessenger.databinding.ListViewChatLogFromMessageItemBinding
import com.nfragiskatos.fragmessenger.databinding.ListViewChatLogToMessageItemBinding
import com.nfragiskatos.fragmessenger.domain.models.ChatMessage
import com.nfragiskatos.fragmessenger.domain.models.User
import com.squareup.picasso.Picasso

private const val MESSAGE_VIEW_TYPE_FROM = 0;
private const val MESSAGE_VIEW_TYPE_TO = 1;

class ChatLogListAdapter :
    ListAdapter<ChatLogMessageItem, RecyclerView.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MESSAGE_VIEW_TYPE_FROM -> FromMessageViewHolder.from(parent)
            MESSAGE_VIEW_TYPE_TO -> ToMessageViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FromMessageViewHolder -> {
                val item = getItem(position) as ChatLogMessageItem.FromMessageItem
                holder.bind(item)
            }

            is ToMessageViewHolder -> {
                val item = getItem(position) as ChatLogMessageItem.ToMessageItem
                holder.bind(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ChatLogMessageItem.FromMessageItem -> MESSAGE_VIEW_TYPE_FROM
            is ChatLogMessageItem.ToMessageItem -> MESSAGE_VIEW_TYPE_TO
        }
    }

    class FromMessageViewHolder private constructor(private val binding: ListViewChatLogFromMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChatLogMessageItem.FromMessageItem) {
            binding.textMessageChatFromItem.text = message.message.text

            Picasso.get().load(message.user.profileImageUrl).into(binding.imageProfileChatFromItem)
        }

        companion object {
            fun from(parent: ViewGroup): FromMessageViewHolder {
                val binding = ListViewChatLogFromMessageItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return FromMessageViewHolder(binding)
            }
        }
    }

    class ToMessageViewHolder private constructor(private val binding: ListViewChatLogToMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChatLogMessageItem.ToMessageItem) {
            binding.textMessageChatToItem.text = message.message.text

            // load user image into
            Picasso.get().load(message.user.profileImageUrl).into(binding.imageProfileChatToItem)
        }

        companion object {
            fun from(parent: ViewGroup): ToMessageViewHolder {
                val binding = ListViewChatLogToMessageItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ToMessageViewHolder(binding)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ChatLogMessageItem>() {
        override fun areItemsTheSame(
            oldItem: ChatLogMessageItem,
            newItem: ChatLogMessageItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ChatLogMessageItem,
            newItem: ChatLogMessageItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}

sealed class ChatLogMessageItem {

    data class FromMessageItem(val message: ChatMessage, val user: User) : ChatLogMessageItem() {

    }

    data class ToMessageItem(val message: ChatMessage, val user: User) : ChatLogMessageItem() {

    }
}