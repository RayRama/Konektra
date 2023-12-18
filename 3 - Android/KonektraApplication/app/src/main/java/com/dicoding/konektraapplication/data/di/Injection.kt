package com.dicoding.konektraapplication.data.di

import android.content.Context
import com.dicoding.konektraapplication.data.api.ApiConfig
import com.dicoding.konektraapplication.data.repository.KonektraRepository

object Injection {
    fun provideKonektraRepository(context: Context) : KonektraRepository{
        val apiService = ApiConfig.getApiServices()
        return KonektraRepository(apiService)
    }
}