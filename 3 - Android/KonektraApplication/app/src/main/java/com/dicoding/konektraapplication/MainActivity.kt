package com.dicoding.konektraapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.konektraapplication.adapter.ListKonektraAdapter
import com.dicoding.konektraapplication.data.Konektra
import com.dicoding.konektraapplication.databinding.ActivityMainBinding
import com.dicoding.konektraapplication.ui.tts.TextToSpeechActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvKonektra: RecyclerView
    private val list = ArrayList<Konektra>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvKonektra = findViewById(R.id.rv_konektra)
        rvKonektra.setHasFixedSize(true)
        list.addAll(getListKonektra())
        showRecyclerList()
        tts()
    }

    private fun getListKonektra(): ArrayList<Konektra> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listKonektra = ArrayList<Konektra>()
        for (i in dataName.indices) {
            val konektra = Konektra(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
            listKonektra.add(konektra)
        }
        return listKonektra
    }

    private fun showRecyclerList() {
        rvKonektra.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListKonektraAdapter(list)
        rvKonektra.adapter = listHeroAdapter
    }

    private fun tts(){
        binding.ivTts.setOnClickListener {
            val intent = Intent(this, TextToSpeechActivity::class.java)
            startActivity(intent)
        }
    }
}