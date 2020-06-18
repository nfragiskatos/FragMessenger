package com.nfragiskatos.fragmessenger.latestmessages


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nfragiskatos.fragmessenger.databinding.ListViewLatestMessageItemBinding

class LatestMessagesListAdapter(private val onClickListener: OnClickListenerLatestMessages) :
    androidx.recyclerview.widget.ListAdapter<String, LatestMessagesListAdapter.LatestMessageViewHolder>(
        DiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestMessageViewHolder {
        return LatestMessageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LatestMessageViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick("CLICKED: $item")
        }
        holder.bind(item)
    }


    class LatestMessageViewHolder private constructor(private val binding: ListViewLatestMessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(str: String) {
            binding.textViewUsernameLatestMessages.text = str
            binding.textviewLatestMessageLatestMessages.text = str
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

    class OnClickListenerLatestMessages(val clickListener: (str: String) -> Unit) {
        fun onClick(str: String) = clickListener(str)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}