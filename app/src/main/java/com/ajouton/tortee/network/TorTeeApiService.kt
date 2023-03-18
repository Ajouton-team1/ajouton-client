package com.ajouton.tortee.network

import com.ajouton.tortee.model.User
import retrofit2.Call
import retrofit2.http.*

interface TorTeeApiService {

    @GET("users")
    suspend fun getUsers(): List<User>

    @POST("auth/login")
    suspend fun signIn(
//        @Header("token") accept: String,
        @Body signInParams: UserSignInRequest
    ): UserSignInResponse


    @POST("member/signIn")
    suspend fun signUp(
        @Body signUpParams: UserSignUpRequest
    ): UserSignUpResponse

    @POST("member/searchMentor")
    suspend fun searchMentor(
        @Body searchMentorParams: GetUserRequest
    ): GetUserResponse?
    
    @GET("/posting/all")
    suspend fun getMentiList(
    ): MentiBulletinResponse

    @GET("/posting")
    suspend fun getOneMenti(
        @Query("postingId") postingId : Int?,
    ): MentiDetailResponse

    @POST("/matching")
    suspend fun makeMatching(
        @Body makeMatchingParams: MakeMatchingRequest
    ): MakeMatchingResponse
//
//    @GET("/matching/myMentor")
//    suspend fun getMyMentors(
//        @Body getMyMentorsParams: GetMyMentorsRequest
//    ): GetMyMentorsResponse
//
//    @GET("/matching/myMentee")
//    suspend fun getMyMentees(
//        @Body getMyMenteesParams: GetMyMenteesRequest
//    ): GetMyMenteesResponse
//
//    @POST("/posting")
//    suspend fun writeMenteePosting(
//        @Body writeMenteePostingParams: WriteMenteePostingRequest
//    ): WriteMenteePostingResponse
//
    @GET("/member/myInfo")
    suspend fun getMyInfo(
        @Query("") getMyInfoParams: GetMyInfoRequest
    ): GetMyInfoResponse?

    @POST("/posting")
    suspend fun writeMenteePosting(
        @Body writeMenteePostingParams: WriteMenteePostingRequest
    ): WriteMenteePostingResponse
}