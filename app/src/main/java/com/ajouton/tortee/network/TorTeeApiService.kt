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
    fun getOneMenti(
        @Query("postingId") postingId : Int?,
    ): MentiDetailResponse

    @POST("/matching")
    fun makeMatching(
        @Body makeMatchingParams: MakeMatchingRequest
    ): MakeMatchingResponse


}