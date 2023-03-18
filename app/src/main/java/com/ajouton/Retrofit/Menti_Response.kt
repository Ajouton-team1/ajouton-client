package com.ajouton.Retrofit

import com.google.gson.annotations.SerializedName

data class Menti_Response(
    var array: ArrayList<MentiArray>
)

data class MentiArray(

    @SerializedName("createdAt")
    var createdAt : String?,
    @SerializedName("updatedAt")
    var updatedAt : String,
    @SerializedName("postingId")
    var postringId : Int,
    @SerializedName("title")
    var title : String,
    @SerializedName("content")
    var content : String,
    @SerializedName("memberId")
    var memberId : Int,
    @SerializedName("member")
    var member: MemberArray,
    )


data class MemberArray(
    @SerializedName("createdAt")
    var createdAt : String?,
    @SerializedName("updatedAt")
    var updatedAt : String,
    @SerializedName("memberId")
    var memberId: Int,
    @SerializedName("email")
    var email : String,
    @SerializedName("password")
    var password : Int,
    @SerializedName("name")
    var name : String,
    @SerializedName("nickname")
    var nickname : String
)