package com.dicoding.konektraapplication.data.api


import com.dicoding.konektraapplication.data.model.SignResponse
import com.dicoding.konektraapplication.data.model.SignResponseItem
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/sign")
    fun getSearchSign(
        @Query("q") query: String
    ): Call<SignResponse>

    @GET("text_to_sign?{title}")
    fun getImageSign(
        @Path("image") image: String
    ): Call<SignResponseItem>

}