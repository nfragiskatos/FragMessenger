package com.nfragiskatos.fragmessenger.chatlog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nfragiskatos.fragmessenger.databinding.ListViewChatFromItemBinding
import com.nfragiskatos.fragmessenger.domain.ChatMessageItem

class MessageListAdapter :
    ListAdapter<ChatMessageItem.FromMessage, MessageListAdapter.ChatFromViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatFromViewHolder {
        return ChatFromViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ChatFromViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message)
    }

    class ChatFromViewHolder private constructor(val binding: ListViewChatFromItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChatMessageItem.FromMessage) {
            binding.textMessageChatFromItem.text = message.messageContent
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

    companion object DiffCallback : DiffUtil.ItemCallback<ChatMessageItem.FromMessage>() {
        override fun areItemsTheSame(
            oldItem: ChatMessageItem.FromMessage,
            newItem: ChatMessageItem.FromMessage
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ChatMessageItem.FromMessage,
            newItem: ChatMessageItem.FromMessage
        ): Boolean {
            return oldItem == newItem
        }
    }
}