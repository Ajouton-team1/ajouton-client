package com.ajouton.tortee.network

import kotlinx.serialization.Serializable

@Serializable
data class MakeMatchingResponse (
    val result: Boolean
)