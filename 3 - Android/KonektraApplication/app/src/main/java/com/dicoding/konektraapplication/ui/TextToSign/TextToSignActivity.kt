package com.dicoding.konektraapplication.ui.TextToSign


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.konektraapplication.MainActivity
import com.dicoding.konektraapplication.data.model.SignResponseItem
import com.dicoding.konektraapplication.databinding.ActivityTextToSignBinding
import com.dicoding.konektraapplication.ui.detail.DetailSignActivity

class TextToSignActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTextToSignBinding
    private val textToSignViewModel by viewModels<TextToSignViewModel> ()
    private val adapter = SignAdapter()

    companion object {
        private val INIT_QUERY = "s"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextToSignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter.setOnItemClickCallback(object : SignAdapter.OnItemClickCallback {
            override fun onItemClicked(data: SignResponseItem) {
                Intent(this@TextToSignActivity, DetailSignActivity::class.java).also {
                    it.putExtra(DetailSignActivity.EXTRA_IMAGE, data.image)
                    it.putExtra(DetailSignActivity.EXTRA_TITLE, data.title)
                    startActivity(it)
                }
            }
        })

        textToSignViewModel.setSearchSign(INIT_QUERY)
        setUpSearchBar()

        val layoutManager = LinearLayoutManager(this)
        binding.rvSign.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvSign.addItemDecoration(itemDecoration)
        binding.rvSign.adapter = adapter

        textToSignViewModel.listSign.observe(this) {items ->
            adapter.submitList(items)
        }

        textToSignViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        back()
    }

    private fun back(){
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpSearchBar() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener{text, actionId, event ->

                searchView.hide()
                textToSignViewModel.setSearchSign(searchView.text.toString())
                false
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}