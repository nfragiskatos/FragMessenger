package com.nfragiskatos.fragmessenger.chatlog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nfragiskatos.fragmessenger.databinding.ListViewChatFromItemBinding
import com.nfragiskatos.fragmessenger.databinding.ListViewChatToItemBinding
import com.nfragiskatos.fragmessenger.domain.ChatMessage

private const val MESSAGE_VIEW_TYPE_FROM = 0;
private const val MESSAGE_VIEW_TYPE_TO = 1;

class MessageListAdapter :
    ListAdapter<ChatMessageItem, RecyclerView.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MESSAGE_VIEW_TYPE_FROM -> ChatFromViewHolder.from(parent)
            MESSAGE_VIEW_TYPE_TO -> ChatToViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChatFromViewHolder -> {
                val item = getItem(position) as ChatMessageItem.FromMessage
                holder.bind(item)
            }
            is ChatToViewHolder -> {
                val item = getItem(position) as ChatMessageItem.ToMessage
                holder.bind(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ChatMessageItem.FromMessage -> MESSAGE_VIEW_TYPE_FROM
            is ChatMessageItem.ToMessage -> MESSAGE_VIEW_TYPE_TO
        }
    }

    class ChatFromViewHolder private constructor(val binding: ListViewChatFromItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChatMessageItem.FromMessage) {
            binding.textMessageChatFromItem.text = message.message.text
        }

        companion object {
            fun from(parent: ViewGroup): ChatFromViewHolder {
                val binding = ListViewChatFromItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ChatFromViewHolder(binding)
            }
        }
    }

    class ChatToViewHolder private constructor(val binding: ListViewChatToItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChatMessageItem.ToMessage) {
            binding.textMessageChatToItem.text = message.message.text
        }

        companion object {
            fun from(parent: ViewGroup): ChatToViewHolder {
                val binding = ListViewChatToItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ChatToViewHolder(binding)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ChatMessageItem>() {
        override fun areItemsTheSame(
            oldItem: ChatMessageItem,
            newItem: ChatMessageItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ChatMessageItem,
            newItem: ChatMessageItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}

sealed class ChatMessageItem {

    data class FromMessage(val message: ChatMessage) : ChatMessageItem() {

    }

    data class ToMessage(val message: ChatMessage) : ChatMessageItem() {

    }
}