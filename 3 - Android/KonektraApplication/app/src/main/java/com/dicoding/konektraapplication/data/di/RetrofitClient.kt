package com.dicoding.konektraapplication.data.di

import com.dicoding.konektraapplication.data.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val API_URL = "https://textosign-z7ofvhkyyq-et.a.run.app/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service: ApiService = retrofit.create(ApiService::class.java)
}