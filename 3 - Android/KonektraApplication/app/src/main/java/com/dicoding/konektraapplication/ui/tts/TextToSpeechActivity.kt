package com.dicoding.konektraapplication.ui.tts

import TextToSpeechViewModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.dicoding.konektraapplication.MainActivity
import com.dicoding.konektraapplication.R
import com.dicoding.konektraapplication.databinding.ActivityTextToSpeechBinding
import com.dicoding.konektraapplication.ui.speechToText.SpeechToTextActivity
import java.util.Locale


class TextToSpeechActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var textToSpeech: TextToSpeech
    private lateinit var binding: ActivityTextToSpeechBinding
    private lateinit var hapus: TextView
    private lateinit var editText: EditText

    private val viewModel: TextToSpeechViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextToSpeechBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textToSpeech = TextToSpeech(this, this)
        hapus = findViewById(R.id.iv_hapus)
        editText = findViewById(R.id.EdText)

        hapus.setOnClickListener {
            onClear()
        }

        binding.apply {
            speakButton.setOnClickListener {
                val text = binding.EdText.text.toString()
                if (text.isNotEmpty()) {
                    speakText(text)
                }
            }
        }
        back()
        toSpeech()

        viewModel.textToSpeech.observe(this, Observer {
            editText.setText(it)
        })
    }

    private fun onClear() {
        viewModel.clearTextToSpeech()
    }

    private fun back() {
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun toSpeech() {
        binding.sttSpeech.setOnClickListener {
            val intent = Intent(this, SpeechToTextActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED
            ) {
                Toast.makeText(this, "Bahasa tidak didukung.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Text-to-Speech siap digunakan.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Gagal menginisialisasi Text-to-Speech.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun speakText(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        viewModel.setTextToSpeech(text)

        if (textToSpeech.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_AVAILABLE ||
            textToSpeech.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_COUNTRY_AVAILABLE
        ) {
            performSomeActionAfterSpeech()
        } else {
            Toast.makeText(
                this,
                "Bahasa tidak didukung oleh Text-to-Speech engine.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun performSomeActionAfterSpeech() {
        Toast.makeText(this, "Teks telah diucapkan!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }
}