package com.ajouton.tortee.network

import com.ajouton.tortee.model.User
import retrofit2.http.*

interface TorTeeApiService {

    @GET("users")
    suspend fun getUsers(): List<User>

    @POST("members/signIn")
    suspend fun signUp(
        @Header("token") accept: String,
        @Body jsonparams: UserSignUpRequest
    ): Boolean

}