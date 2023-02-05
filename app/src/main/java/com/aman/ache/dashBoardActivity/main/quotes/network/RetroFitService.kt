package com.aman.ache.dashBoardActivity.main.quotes.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://sleepy-caverns-04411.herokuapp.com/"
//private const val BASE_URL = "http://54.89.172.135:3000/"

class RetroFitService {
    fun getInstance():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}