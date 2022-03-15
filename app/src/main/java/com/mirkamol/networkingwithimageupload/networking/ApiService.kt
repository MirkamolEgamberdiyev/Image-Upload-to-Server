package com.mirkamol.networkingwithimageupload.networking

import com.mirkamol.networkingwithimageupload.model.CatItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    fun getPhotoes(@Query("category_ids")id:Int,
        @Query("page")page:Int, @Query("limit") limit:Int):Call<List<CatItem>>
}