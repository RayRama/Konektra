package com.dicoding.konektraapplication.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Konektra(
    val name: String,
    val description: String,
    val photo: Int
): Parcelable
