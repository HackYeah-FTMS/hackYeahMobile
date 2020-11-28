package com.hackyeah.app.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.hackyeah.app.databinding.ActivityMainBinding
import com.hackyeah.app.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun inject() {
        // do nothing
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {
        fun intentFor(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

}