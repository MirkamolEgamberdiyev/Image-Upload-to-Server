package com.mirkamol.networkingwithimageupload.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val IS_TESTER = true
    val SERVER_DEVELOPMENT ="https://api.thecatapi.com/v1/images/"
    val SERVER_PRODUCTION = "https://api.thecatapi.com/v1/images/"

    var retrofit = Retrofit.Builder().baseUrl(server()).addConverterFactory(GsonConverterFactory.create()).build()
    private fun server():String{
        return if (IS_TESTER){
            SERVER_DEVELOPMENT
        }else{
            SERVER_PRODUCTION
        }
    }

    val postService:ApiService = retrofit.create(ApiService::class.java)
}