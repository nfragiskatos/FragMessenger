package com.nfragiskatos.fragmessenger.newmessage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nfragiskatos.fragmessenger.databinding.ListViewUserItemBinding
import com.nfragiskatos.fragmessenger.domain.User
import com.squareup.picasso.Picasso

class UserListAdapter(private val onClickListener: OnClickListenerUserList) : ListAdapter<User, UserListAdapter.UserViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(user)
        }
        holder.bind(user)
    }

    class UserViewHolder private constructor(val binding: ListViewUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.textViewUserName.text = user.username
            Picasso.get().load(user.profileImageUrl).into(binding.imageViewProfilePicture)

        }

        companion object {
            fun from(parent: ViewGroup): UserViewHolder {
                val binding = ListViewUserItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return UserViewHolder(binding)
            }
        }
    }

    class OnClickListenerUserList(val clickListener: (user: User) -> Unit) {
        fun onClick(user: User) = clickListener(user)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}