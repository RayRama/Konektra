package com.dicoding.konektraapplication.ui.tts

import HistoryAdapter
import TextToSpeechViewModel
import android.content.Intent
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.konektraapplication.R
import com.dicoding.konektraapplication.databinding.ActivityTextToSpeechBinding
import java.util.Locale


class TextToSpeechActivity : AppCompatActivity(),TextToSpeech.OnInitListener {

    private lateinit var textToSpeech: TextToSpeech
    private lateinit var speakButton: Button
    private lateinit var editText: EditText
    private lateinit var btnHapus: Button
    private lateinit var btnSimpan: Button
    private lateinit var btnHistory: Button
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var binding: ActivityTextToSpeechBinding
    private lateinit var textToSpeechViewModel: TextToSpeechViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextToSpeechBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textToSpeechViewModel = ViewModelProvider(this).get(TextToSpeechViewModel::class.java)
        historyAdapter = HistoryAdapter(textToSpeechViewModel.historyList)

//        historyRecyclerView = findViewById(R.id.historyRecyclerView)
//        historyRecyclerView.layoutManager = LinearLayoutManager(this)
//        historyRecyclerView.adapter = historyAdapter

        speakButton = findViewById(R.id.speakButton)
        editText = findViewById(R.id.EdText)
        btnHapus = findViewById(R.id.btnHapus)
        btnSimpan = findViewById(R.id.btnSimpan)
        btnHistory = findViewById(R.id.btnHistory)
        textToSpeech = TextToSpeech(this,this)


        speakButton.setOnClickListener {
            val text = editText.text.toString()
            if(text.isNotEmpty()){
                speakText(text)
                textToSpeechViewModel.addToHistory(text)
                historyAdapter.notifyDataSetChanged()
            }
        }

        btnHapus.setOnClickListener {
            editText.text.clear()
        }

        btnSimpan.setOnClickListener {
            val text = editText.text.toString()
            if (text.isNotEmpty()) {
                textToSpeechViewModel.addToHistory(text)
                showToast("Teks disimpan ke dalam history.")
            }
        }

        btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Inisialisasi sukses, atur bahasa suara ke bahasa Inggris (US)
            val result = textToSpeech.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED
            ) {
                // Bahasa tidak didukung, mungkin perlu mengunduh data bahasa (tambahkan logika pengunduhan di sini)
                // Atau berikan umpan balik kepada pengguna bahwa bahasa tidak didukung
                Toast.makeText(this, "Bahasa tidak didukung.", Toast.LENGTH_SHORT).show()
            } else {
                // Inisialisasi sukses dan bahasa didukung
                Toast.makeText(this, "Text-to-Speech siap digunakan.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Gagal menginisialisasi Text-to-Speech.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun speakText(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)

        performSomeActionAfterSpeech()
        textToSpeechViewModel.addToHistory(text)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



    private fun performSomeActionAfterSpeech() {

        Toast.makeText(this, "Teks telah diucapkan!", Toast.LENGTH_SHORT).show()
//
//        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        val ringtone = RingtoneManager.getRingtone(applicationContext, notification)
//        ringtone.play()
    }

    override fun onDestroy() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }
}