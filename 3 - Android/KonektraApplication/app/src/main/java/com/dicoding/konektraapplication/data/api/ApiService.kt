package com.dicoding.konektraapplication.data.api


import com.dicoding.konektraapplication.data.model.SignResponse
import com.dicoding.konektraapplication.data.model.SignResponseItem
import com.dicoding.konektraapplication.data.pref.Model
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET
    fun getData(@Url url: String) : Call<List<Model>>
}