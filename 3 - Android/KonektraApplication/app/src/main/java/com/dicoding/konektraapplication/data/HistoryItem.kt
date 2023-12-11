package com.dicoding.konektraapplication.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class HistoryItem(
    val text: String,
    val date: Date
    ): Parcelable