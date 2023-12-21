package com.dicoding.konektraapplication.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.konektraapplication.databinding.ActivityDetailSignBinding

class DetailSignActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSignBinding
    private val detailUserViewModel by viewModels<DetailSignViewModel> {
        ViewModelProvider.AndroidViewModelFactory(application)
    }

    companion object{
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_TITLE = "extra_title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra(EXTRA_TITLE)
        val image = intent.getStringExtra(EXTRA_IMAGE)

        detailUserViewModel.setDetailSign(title.toString())
        detailUserViewModel.title.observe(this, {
            if (it != null) {
                with(binding) {
                    tvTitle.text = it.title
                    Glide.with(this@DetailSignActivity)
                        .load(it.image)
                        .centerCrop()
                        .into(ivSign)
                }
            }
        })

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

}

