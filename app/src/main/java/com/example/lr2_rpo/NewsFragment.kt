package com.example.lr2_rpo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.webkit.WebViewClient
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_news.*


class NewsFragment : Fragment() {

    lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun setLink(link: String){
        url = link
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView.webViewClient = WebViewClient()

        // this will load the url of the website
        webView.loadUrl(/*"https://www.geeksforgeeks.org/"*/url)

        // this will enable the javascript settings, it can also allow xss vulnerabilities
        webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        webView.settings.setSupportZoom(true)
    }

    // if you press Back button this code will work
   /* override fun onBackPressed() {
        // if your webview can go back it will go back
        if (webView.canGoBack())
            webView.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            super.onBackPressed()
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NewsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}