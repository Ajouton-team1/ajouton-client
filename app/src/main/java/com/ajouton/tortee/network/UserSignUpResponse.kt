package com.ajouton.tortee.network

import com.google.gson.annotations.SerializedName

data class UserSignUpResponse(
    @SerializedName("result")
    var result : Int
)

