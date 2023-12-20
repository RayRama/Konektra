package com.dicoding.konektraapplication.ui.sst

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.dicoding.konektraapplication.R
import com.dicoding.konektraapplication.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webView = findViewById<WebView>(R.id.webview)
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView, url: String) {
                view.loadUrl("")
            }
        }
        webView.webChromeClient = object :  WebChromeClient(){
            override fun onJsAlert(
                view: WebView,
                url: String,
                message: String,
                result: JsResult
            ) : Boolean {
                Toast.makeText(this@WebViewActivity, message, Toast.LENGTH_LONG).show()
                result.confirm()
                return true
            }
        }
        webView.loadUrl("https://jkt48.com/")
    }
}