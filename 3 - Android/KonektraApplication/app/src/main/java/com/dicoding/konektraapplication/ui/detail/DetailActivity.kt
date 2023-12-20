package com.dicoding.konektraapplication.ui.detail

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.dicoding.konektraapplication.MainActivity
import com.dicoding.konektraapplication.data.Konektra
import com.dicoding.konektraapplication.R
import com.dicoding.konektraapplication.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object{
        const val EXTRA_KONEKTRA = "extra_konektra"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataKonektra = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Konektra>(EXTRA_KONEKTRA, Konektra::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Konektra>(EXTRA_KONEKTRA)
        }

        val tvName : TextView = findViewById(R.id.tv_item_name)
//        val tvSummary : TextView = findViewById(R.id.tv_summary)
        val tvDescription : TextView = findViewById(R.id.tv_detail_description)
        val imgPhoto: ImageView = findViewById(R.id.img_item_photo)

        tvName.text = dataKonektra?.name
//        tvSummary.text = "Description"
        tvDescription.text = dataKonektra?.description
        imgPhoto.setImageResource(dataKonektra!!.photo)

        back()
    }

    private fun back(){
        binding.btnKembali.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}