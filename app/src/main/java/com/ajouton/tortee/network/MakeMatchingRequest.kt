package com.ajouton.tortee.network

import kotlinx.serialization.Serializable

@Serializable
data class MakeMatchingRequest(
    val mentorId: Int,
    val menteeId: Int
)
