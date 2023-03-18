package com.ajouton.tortee.network

import kotlinx.serialization.Serializable

@Serializable
data class UserSignUpRequest(
    var email: String,
    var name: String,
    var nickname: String,
    var password: String
)
