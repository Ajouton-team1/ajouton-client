package com.ajouton.tortee.network

import com.ajouton.tortee.model.User
import kotlinx.serialization.Serializable

@Serializable
data class GetUserRequest (
    val tags: List<String>
)