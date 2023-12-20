package com.dicoding.konektraapplication.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Response(

	@field:SerializedName("Response")
	val response: List<ResponseItem?>? = null
):Parcelable

@Parcelize
data class ResponseItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("title")
	val title: String? = null
):Parcelable
