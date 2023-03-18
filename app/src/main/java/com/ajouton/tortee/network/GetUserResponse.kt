package com.ajouton.tortee.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Objects

@Serializable
data class GetUserResponse(
    val members: List<UserData>
)

@Serializable
data class UserData(
    val createdAt: String,
    val updatedAt: String,
    val memberId: Int,
    val email: String,
    val password: String,
    val name: String,
    val nickname: String,
    val description: String,
    val score: Int,
    val memberTags: List<MemberTags>
)

@Serializable
data class MemberTags(
    val createdAt: String,
    val updatedAt: String,
    val memberTagId: Int,
    val memberId: Int,
    val tagId: Int,
    val tag: Tag
)

@Serializable
data class Tag(
    val createdAt: String,
    val updatedAt: String,
    val tagId: Int,
    val name: String
)