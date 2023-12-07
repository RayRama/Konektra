package com.dicoding.konektraapplication.boardingpage

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import androidx.constraintlayout.motion.widget.MotionLayout
import com.dicoding.konektraapplication.MainActivity
import com.dicoding.konektraapplication.R
import com.dicoding.konektraapplication.databinding.ActivityOnBoardingPageBinding

class onBoardingPage : AppCompatActivity() {

    private lateinit var binding : ActivityOnBoardingPageBinding
    private lateinit var btn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupView()
        next()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }


    private fun next(){
        binding.btnFinish.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


}