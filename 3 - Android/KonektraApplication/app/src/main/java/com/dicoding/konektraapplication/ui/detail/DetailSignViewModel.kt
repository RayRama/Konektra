package com.dicoding.konektraapplication.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.konektraapplication.data.api.ApiConfig
import com.dicoding.konektraapplication.data.model.SignResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailSignViewModel (application: Application) : AndroidViewModel(application) {

    private val _title = MutableLiveData<SignResponseItem>()
    val title: LiveData<SignResponseItem> = _title

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setDetailSign(title: String){
        _isLoading.value = true
        val client = ApiConfig.getApiServices().getImageSign(title)
        client.enqueue(object : Callback<SignResponseItem> {
            override fun onResponse(
                call: Call<SignResponseItem>,
                response: Response<SignResponseItem>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _title.value = response.body()
                }
            }

            override fun onFailure(call: Call<SignResponseItem>, t: Throwable) {
                _isLoading.value = false
                Log.d("Failure", t.message.toString())
            }
        })
    }
}