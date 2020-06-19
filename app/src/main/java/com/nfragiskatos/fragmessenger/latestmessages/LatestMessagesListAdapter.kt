package com.nfragiskatos.fragmessenger.latestmessages


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nfragiskatos.fragmessenger.databinding.ListViewLatestMessageItemBinding
import com.nfragiskatos.fragmessenger.domain.ChatMessage

class LatestMessagesListAdapter(private val onClickListener: OnClickListenerLatestMessages) :
    androidx.recyclerview.widget.ListAdapter<ChatMessage, LatestMessagesListAdapter.LatestMessageViewHolder>(
        DiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestMessageViewHolder {
        return LatestMessageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LatestMessageViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.bind(item)
    }


    class LatestMessageViewHolder private constructor(private val binding: ListViewLatestMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: ChatMessage) {
            binding.textViewUsernameLatestMessages.text = message.fromId
            binding.textviewLatestMessageLatestMessages.text = message.text
        }

        companion object {
            fun from(parent: ViewGroup): LatestMessageViewHolder {
                val binding = ListViewLatestMessageItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return LatestMessageViewHolder(binding)
            }
        }
    }

    class OnClickListenerLatestMessages(val clickListener: (message: ChatMessage) -> Unit) {
        fun onClick(message: ChatMessage) = clickListener(message)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
        override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem == newItem
        }
    }
}