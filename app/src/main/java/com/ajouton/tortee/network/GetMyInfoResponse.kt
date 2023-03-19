package com.ajouton.tortee.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GetMyInfoResponse(
    val createdAt: String = "",
    val updatedAt: String = "",
    val memberId: Int = 0,
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val nickname: String = "",
    val description: String = "",
    @SerializedName("memberTags")
    val myMemberTags: List<MyMemberTags> = listOf(MyMemberTags())
)

@Serializable
data class MyMemberTags(
    val createdAt: String = "",
    val updatedAt: String = "",
    val memberTagId: Int = 0,
    val memberId: Int = 0,
    val tagId: Int = 0,
    val tag: MyTag = MyTag()
)

@Serializable
data class MyTag(
    val createdAt: String = "",
    val updatedAt: String = "",
    val tagId: Int = 0,
    val name: String = ""
)