package com.dicoding.konektraapplication.ui.TextToSign


import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.konektraapplication.MainActivity
import com.dicoding.konektraapplication.data.api.ApiService
import com.dicoding.konektraapplication.data.di.RetrofitClient
import com.dicoding.konektraapplication.data.model.SignResponseItem
import com.dicoding.konektraapplication.data.pref.Model
import com.dicoding.konektraapplication.databinding.ActivityTextToSignBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TextToSignActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTextToSignBinding
    private lateinit var mService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextToSignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mService = RetrofitClient.service
        binding.rvTTS.setHasFixedSize(true)
        binding.rvTTS.layoutManager = LinearLayoutManager(this)

        binding.btnKirim.setOnClickListener {
            val teks = binding.edKata.text.toString()
            binding.edKata.setText("")

            mService.getData("https://textosign-z7ofvhkyyq-et.a.run.app/text_to_sign?text=$teks")
                .enqueue(object : Callback<List<Model>> {
                    @RequiresApi(Build.VERSION_CODES.P)
                    override fun onResponse(call: Call<List<Model>>, response: Response<List<Model>>) {
                        if (response.isSuccessful) {
                            val dataList = response.body()
                            dataList?.let {
                                binding.rvTTS.adapter = SignAdapter(it)
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<Model>>, t: Throwable) {
                        Log.e("FAIL", "FAIL $t")
                    }

                })
        }

        backMain()

    }

    private fun backMain(){
        binding.btnBackText.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}