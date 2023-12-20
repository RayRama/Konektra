package com.dicoding.konektraapplication.ui.setting

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
import androidx.datastore.preferences.core.Preferences
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.dicoding.konektraapplication.R
import java.util.prefs.PreferencesFactory

class SettingsFragment : PreferenceFragmentCompat() {

    private var creditPreferenceClickListener: (() -> Unit)? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preference, rootKey)
        val darkMode: ListPreference? = findPreference(getString(R.string.pref_key_dark))
        darkMode?.setOnPreferenceChangeListener{_, newValue ->
            val value = newValue.toString()
            if (value == getString(R.string.pref_dark_auto)){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    updateTheme(com.dicoding.konektraapplication.ui.setting.NightMode.AUTO.value)
                else
                    updateTheme(com.dicoding.konektraapplication.ui.setting.NightMode.ON.value)
            }else if(value == getString(R.string.pref_dark_off))
                updateTheme(com.dicoding.konektraapplication.ui.setting.NightMode.OFF.value)
            else
                updateTheme(com.dicoding.konektraapplication.ui.setting.NightMode.ON.value)
            true
        }

//        findPreference<Preference>("credit_preference")?.setOnPreferenceClickListener {
//            // Jika listener tidak null, panggil listener
//            creditPreferenceClickListener?.invoke()
//            true
//        }
    }

//    fun setOnCreditPreferenceClickListener(listener: () -> Unit) {
//        creditPreferenceClickListener = listener
//        Log.d("SettingsFragment", "setOnCreditPreferenceClickListener set")
//    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}


