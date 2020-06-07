package com.nfragiskatos.fragmessenger

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nfragiskatos.fragmessenger.newmessage.UserListAdapter


@BindingAdapter("listData")
fun bindUserRecyclerView(recyclerView: RecyclerView, data: List<String>?) {
    val adapter = recyclerView.adapter as UserListAdapter
    adapter.submitList(data)
}