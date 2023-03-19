package com.ajouton.tortee.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GetMyInfoResponse(
    val createdAt: String,
    val updatedAt: String,
    val memberId: Int,
    val email: String,
    val password: String,
    val name: String,
    val nickname: String,
    val description: String,
    @SerializedName("memberTags")
    val myMemberTags: List<MyMemberTags>
)

@Serializable
data class MyMemberTags(
    val createdAt: String,
    val updatedAt: String,
    val memberTagId: Int,
    val memberId: Int,
    val tagId: Int,
    val tag: MyTag
)

@Serializable
data class MyTag(
    val createdAt: String,
    val updatedAt: String,
    val tagId: Int,
    val name: String
)