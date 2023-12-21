package com.dicoding.konektraapplication.ui.TextToSign

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.konektraapplication.data.model.SignResponseItem
import com.dicoding.konektraapplication.databinding.ItemRowSignBinding

class SignAdapter : ListAdapter<SignResponseItem, SignAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowSignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    inner class MyViewHolder(val binding: ItemRowSignBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: SignResponseItem) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
            with(binding) {
                Glide.with(itemView)
                    .load(user.image)
                    .centerCrop()
                    .into(ivSign)
                tvTitle.text = user.title
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SignResponseItem>() {
            override fun areItemsTheSame(oldItem: SignResponseItem, newItem: SignResponseItem): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: SignResponseItem, newItem: SignResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: SignResponseItem)
    }
}