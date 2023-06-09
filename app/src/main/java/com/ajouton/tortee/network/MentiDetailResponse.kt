package com.ajouton.tortee.network

import com.google.gson.annotations.SerializedName

data class MentiDetailResponse(
    @SerializedName("createdAt")
    var createdAt : String,
    @SerializedName("updatedAt")
    var updatedAt : String,
    @SerializedName("postingId")
    var postingId : Int,
    @SerializedName("title")
    var title : String,
    @SerializedName("content")
    var content : String,
    @SerializedName("memberId")
    var memberId :Int,
    @SerializedName("member")
    var member : MemberInfo
)

