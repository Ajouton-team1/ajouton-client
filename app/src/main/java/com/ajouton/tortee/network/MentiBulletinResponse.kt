package com.ajouton.tortee.network

import com.google.gson.annotations.SerializedName

data class MentiBulletinResponse(
    var mentiList : ArrayList<menti>
)

data class menti(
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

data class MemberInfo(
    @SerializedName("createdAt")
    var createdAt : String,
    @SerializedName("updatedAt")
    var updatedAt : String,
    @SerializedName("memberId")
    var memberId : Int,
    @SerializedName("email")
    var email : String,
    @SerializedName("password")
    var password : Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("nickname")
    var nickname: String
)
