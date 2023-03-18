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

    @GET("/recipes/search")
    fun get_Search(
        @Query("keyword") keyword : String?,
        @Header("x-access-token") token: String?
    ): Call<Search_Response>

}