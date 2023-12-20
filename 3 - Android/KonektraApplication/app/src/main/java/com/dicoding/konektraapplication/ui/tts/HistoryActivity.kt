package com.dicoding.konektraapplication.ui.tts

import HistoryAdapter
import TextToSpeechViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.konektraapplication.R
import com.dicoding.konektraapplication.data.HistoryItem
import java.util.Date

class HistoryActivity : AppCompatActivity() {

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var textToSpeechViewModel: TextToSpeechViewModel


    val historyItems = listOf(
        HistoryItem("Text 1", Date()),
        HistoryItem("Text 2", Date()),
        // Tambahkan item history lainnya
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        textToSpeechViewModel = ViewModelProvider(this).get(TextToSpeechViewModel::class.java)
        historyAdapter = HistoryAdapter(textToSpeechViewModel.historyList)

        historyRecyclerView = findViewById(R.id.historyRecyclerView)
        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.adapter = historyAdapter


        val historyRecyclerView: RecyclerView = findViewById(R.id.historyRecyclerView)
        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.adapter = historyAdapter



        val clearHistoryButton: Button = findViewById(R.id.clearHistoryButton)
        clearHistoryButton.setOnClickListener {
            // Panggil fungsi clearHistory pada ViewModel
            textToSpeechViewModel.clearHistory()
        }


    }

}
