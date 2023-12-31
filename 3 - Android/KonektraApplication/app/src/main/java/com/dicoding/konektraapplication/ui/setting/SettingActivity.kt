package com.dicoding.konektraapplication.ui.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.konektraapplication.R
import com.dicoding.konektraapplication.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        val settingsFragment = supportFragmentManager.findFragmentById(R.id.settings) as? SettingsFragment
//        settingsFragment?.setOnCreditPreferenceClickListener {
//            showCreditMenu()
//        }
    }


//    private fun showCreditMenu() {
//        val intent = Intent(this, CreditActivity::class.java)
//        startActivity(intent)
//    }

}