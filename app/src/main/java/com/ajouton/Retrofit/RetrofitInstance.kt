package com.ajouton.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance{

    private var  BASE_URL = "http://zipdabang.store:3000"

    val retrofit = retrofit2.Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}

