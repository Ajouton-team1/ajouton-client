package com.ajouton.tortee.data

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("result")
    val result : Boolean,
    @SerializedName("userId")
    val userId : Int
)