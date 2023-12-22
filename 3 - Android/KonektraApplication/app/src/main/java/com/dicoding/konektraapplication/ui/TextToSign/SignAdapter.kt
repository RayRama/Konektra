package com.dicoding.konektraapplication.ui.TextToSign

import android.graphics.ImageDecoder
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi

import androidx.recyclerview.widget.RecyclerView

import com.dicoding.konektraapplication.data.pref.Model
import com.dicoding.konektraapplication.databinding.ItemRowSignLanguageBinding
import java.nio.ByteBuffer


class SignAdapter(private val tts: List<Model>): RecyclerView.Adapter<SignAdapter.TTSViewHolder>() {
    class TTSViewHolder(private val binding: ItemRowSignLanguageBinding): RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.P)
        fun bind(item: Model) {
            binding.tvKata.text = item.title
            if (item.image.isNotEmpty()) {
                val drawable = decodeBase64ToDrawable(item.image)
                if (drawable is AnimatedImageDrawable) {
                    binding.ivKata.setImageDrawable(drawable)
                    drawable.start()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TTSViewHolder {
        val binding = ItemRowSignLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TTSViewHolder(binding)
    }

    override fun getItemCount(): Int = tts.size

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: TTSViewHolder, position: Int) {
        holder.bind(tts[position])
    }
}

@RequiresApi(Build.VERSION_CODES.P)
private fun decodeBase64ToDrawable(input: String?): Drawable {
    val decodedBytes = Base64.decode(input, Base64.DEFAULT)
    val byteBuffer = ByteBuffer.wrap(decodedBytes)
    return ImageDecoder.decodeDrawable(ImageDecoder.createSource(byteBuffer))
}