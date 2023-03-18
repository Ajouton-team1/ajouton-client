package com.ajouton.tortee.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSignInRequest(
    @SerialName(value = "email")
    var email: String,
    @SerialName(value = "password")
    var password: String
)
