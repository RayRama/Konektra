package com.dicoding.konektraapplication.ui.speechToText

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.dicoding.konektraapplication.MainActivity
import com.dicoding.konektraapplication.R
import com.dicoding.konektraapplication.databinding.ActivitySpeechToTextBinding
import com.dicoding.konektraapplication.ui.tts.TextToSpeechActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale
import java.util.Objects

class SpeechToTextActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpeechToTextBinding
    private lateinit var hapusSpeech: TextView
    private lateinit var tvSpeech: TextView
    private lateinit var micSpeech: FloatingActionButton
    private val REQUEST_CODE_SPEECH_INPUT = 1

    private val viewModel: SpeechToTextViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpeechToTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hapusSpeech = findViewById(R.id.iv_hapusSpeech)
        tvSpeech = findViewById(R.id.EdTextSpeech)
        micSpeech = findViewById(R.id.speakButtonSpeech)

        micSpeech.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speech To Text")

            try{
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                Toast.makeText(this, "" + e.message, Toast.LENGTH_LONG).show()
            }
        }

        hapusSpeech.setOnClickListener {
            clearSpeech()
        }

        viewModel.speechText.observe(this, Observer {
            tvSpeech.text = it
        })

        back()
        home()
    }

    private fun back() {
        binding.btnBackSpeech.setOnClickListener {
            val intent = Intent(this, TextToSpeechActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_CODE_SPEECH_INPUT){
            if (resultCode == RESULT_OK && data != null){
                val res : ArrayList<String> = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                viewModel.setSpeechText(Objects.requireNonNull(res)[0])
            }
        }
    }

    private fun clearSpeech(){
        viewModel.clearSpeechText()
    }

    private fun home(){
        binding.homeSpeech.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}