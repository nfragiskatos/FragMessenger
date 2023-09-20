package com.nfragiskatos.fragmessenger.presentation.ui.latestmessages


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nfragiskatos.fragmessenger.R
import com.nfragiskatos.fragmessenger.databinding.ListViewLatestMessageItemBinding
import com.nfragiskatos.fragmessenger.domain.models.ChatMessage
import com.nfragiskatos.fragmessenger.domain.models.User
import com.squareup.picasso.Picasso

class LatestMessagesListAdapter(private val onClickListener: OnClickListenerLatestMessages) :
    androidx.recyclerview.widget.ListAdapter<LatestMessageItem, LatestMessagesListAdapter.LatestMessageViewHolder>(
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

        fun bind(latestMessage: LatestMessageItem) {
            binding.textViewUsernameLatestMessages.text = latestMessage.user.username
            binding.textviewLatestMessageLatestMessages.text = latestMessage.message.text

            if (latestMessage.user.profileImageUrl.isNullOrBlank()) {
                binding.imageView.setImageResource(R.drawable.baseline_person_24)
            } else {
                val context = binding.imageView.context
                val drawable = context.resources.getDrawable(R.drawable.baseline_person_24)
                Picasso.get().load(latestMessage.user.profileImageUrl)
                    .placeholder(drawable)
                    .error(drawable)
                    .into(binding.imageView)
            }


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

    class OnClickListenerLatestMessages(val clickListener: (latestMessage: LatestMessageItem) -> Unit) {
        fun onClick(latestMessage: LatestMessageItem) = clickListener(latestMessage)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<LatestMessageItem>() {
        override fun areItemsTheSame(
            oldItem: LatestMessageItem,
            newItem: LatestMessageItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: LatestMessageItem,
            newItem: LatestMessageItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}

data class LatestMessageItem(val message: ChatMessage, val user: User)