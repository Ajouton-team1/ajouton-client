package com.ajouton.tortee.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSignInResponse (
    @SerialName(value = "result")
    val result: Boolean,
    @SerialName(value = "id")
    val id: Int
)