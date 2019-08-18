package com.example.kotlin_mvp_movie.ui.detail

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_mvp_movie.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val detailUrl = intent.getStringExtra("url") as String
        applyWebViewSettings(webView_main)
        initWebView(detailUrl)
    }

    private val mWebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            if (newProgress == 100) {
                progressBar_webView.visibility = View.GONE
            }
        }
    }

    private val mWebViewClient = object: WebViewClient(){
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            url: String?
        ): Boolean {
            view!!.loadUrl(url)
            return true
        }
    }

    private fun initWebView(url: String) {
        webView_main.apply {
            webChromeClient = mWebChromeClient
            webViewClient = mWebViewClient
            applyWebViewSettings(this)
            loadUrl(url)
        }
    }

    private fun applyWebViewSettings(webView: WebView) {
        webView.settings.apply {
            loadWithOverviewMode = false
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = false
        }
    }
}

