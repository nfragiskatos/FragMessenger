package com.nfragiskatos.fragmessenger.domain.models


data class ChatMessage(
    val id: String,
    val text: String,
    val fromId: String,
    val toId: String,
    val timestamp: Long
) {
    constructor() : this("", "", "", "", -1L)
}

