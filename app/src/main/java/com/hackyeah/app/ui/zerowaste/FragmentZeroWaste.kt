package com.hackyeah.app.ui.zerowaste

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.hackyeah.app.databinding.FragmentZeroWasteBinding
import com.hackyeah.app.ui.base.BaseFragment
import com.hackyeah.app.ui.main.MainActivity

class FragmentZeroWaste : BaseFragment() {

    private var _binding: FragmentZeroWasteBinding? = null
    private val binding get() = _binding!!

    private val WEB_VIEW_URL = "https://hackyeah-frontend.web.app/"

    override fun inject() {
        // do nothing
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentZeroWasteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).showBottomNavigation()
        (requireActivity() as MainActivity).showHUD()
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webview.loadUrl(WEB_VIEW_URL)
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                (requireActivity() as MainActivity).hideHUD()
            }
        }
        binding.webview.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        binding.webview.settings.domStorageEnabled = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}