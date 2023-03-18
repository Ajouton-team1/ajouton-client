package com.ajouton.tortee.network

import com.ajouton.tortee.model.User
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
}