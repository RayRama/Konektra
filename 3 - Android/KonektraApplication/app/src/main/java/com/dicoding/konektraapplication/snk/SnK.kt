package com.dicoding.konektraapplication.snk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import com.dicoding.konektraapplication.MainActivity
import com.dicoding.konektraapplication.R
import com.dicoding.konektraapplication.boardingpage.onBoardingPage

class SnK : AppCompatActivity() {

    private lateinit var agreeCheckBox: CheckBox
    private lateinit var continueButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sn_k)

        agreeCheckBox = findViewById(R.id.agreeCheckBox)
        continueButton = findViewById(R.id.continueButton)

        continueButton.setOnClickListener {
            onContinueButtonClick(it)
        }

        agreeCheckBox.setOnCheckedChangeListener { _, isChecked ->
            // Update the button's state based on whether the checkbox is checked or not
            continueButton.isClickable = isChecked
            continueButton.alpha = if (isChecked) 1.0f else 0.5f
        }
    }

    fun onContinueButtonClick(view: View) {
        if (agreeCheckBox.isChecked) {
            val intent = Intent(this, onBoardingPage::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Anda harus menyetujui Persyaratan dan Ketentuan", Toast.LENGTH_SHORT).show()
        }
    }
}