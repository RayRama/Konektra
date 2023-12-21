package com.dicoding.konektraapplication.ui.speechToText

// src/main/java/com/dicoding/konektraapplication/ui/speechToText/SpeechToTextViewModel.kt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SpeechToTextViewModel : ViewModel() {

    private val _speechText = MutableLiveData<String>()
    val speechText: LiveData<String>
        get() = _speechText

    fun setSpeechText(text: String) {
        _speechText.value = text
    }

    fun clearSpeechText() {
        _speechText.value = ""
    }
}
