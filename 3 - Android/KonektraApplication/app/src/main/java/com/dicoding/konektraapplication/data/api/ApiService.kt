package com.dicoding.konektraapplication.data.api

import com.dicoding.konektraapplication.data.model.ResponseItem
import retrofit2.http.DELETE
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("tts/{image}")
    suspend fun getImage(
        @Path("image") image: String
    ):ResponseItem

    @GET("tts/{image}/{title}")
    suspend fun getTitle(
        @Path("title") title:String
    ):ResponseItem

    @FormUrlEncoded
    @POST("tts/{image}")
    suspend fun addImage(
        @Path("image") image: String,
        @Path("title") title: String
    )

    @DELETE("tts/{image}")
    suspend fun deleteImage(
        @Path("image") image: String
    )
}