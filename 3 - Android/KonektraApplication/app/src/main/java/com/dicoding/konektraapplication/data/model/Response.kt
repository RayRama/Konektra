package com.dicoding.konektraapplication.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignResponse(

	@field:SerializedName("Response")
	val signResponse: List<SignResponseItem>
):Parcelable

@Parcelize
data class SignResponseItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("title")
	val title: String
):Parcelable
