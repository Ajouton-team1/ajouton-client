package com.ajouton.tortee.data

import com.google.gson.annotations.SerializedName

data class Logout(
    @SerializedName("result")
    val result : Boolean
    )