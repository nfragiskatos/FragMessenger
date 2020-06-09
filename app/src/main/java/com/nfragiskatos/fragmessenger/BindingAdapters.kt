package com.nfragiskatos.fragmessenger

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nfragiskatos.fragmessenger.newmessage.UserListAdapter
import com.nfragiskatos.fragmessenger.register.User


@BindingAdapter("listData")
fun bindUserRecyclerView(recyclerView: RecyclerView, data: List<User>?) {
    val adapter = recyclerView.adapter as UserListAdapter
    adapter.submitList(data)
}