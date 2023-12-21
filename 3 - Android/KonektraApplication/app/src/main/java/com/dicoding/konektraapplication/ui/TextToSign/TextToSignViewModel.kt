package com.dicoding.konektraapplication.ui.TextToSign

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.konektraapplication.data.api.ApiConfig
import com.dicoding.konektraapplication.data.model.SignResponse
import com.dicoding.konektraapplication.data.model.SignResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TextToSignViewModel : ViewModel(){

    private val _listSign = MutableLiveData<List<SignResponseItem>>()
    val listSign: LiveData<List<SignResponseItem>> = _listSign

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setSearchSign(query: String){
        _isLoading.value = true
        val client = ApiConfig.getApiServices().getSearchSign(query)
        client.enqueue(object : Callback<SignResponse> {
            override fun onResponse(
                call: Call<SignResponse>,
                response: Response<SignResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listSign.value = response.body()?.signResponse
                }
            }

            override fun onFailure(call: Call<SignResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("Failure", t.message.toString())
            }

        })
    }

}