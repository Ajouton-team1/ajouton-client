package com.ajouton.tortee.network

data class WriteMenteePostingRequest(
    val title: String,
    val content: String,
    val memberId: Int,
    val tags: List<String>
)
