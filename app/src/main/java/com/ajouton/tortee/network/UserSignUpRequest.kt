package com.ajouton.tortee.network

data class UserSignUpRequest(
    val email: String,
    val password: String,
    val name: String,
    val nickname: String,
    val description: String,
    val tags: List<String>
)
