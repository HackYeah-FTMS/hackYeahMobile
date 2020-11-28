package com.hackyeah.app.ui.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setOrientation()
        hideActionBar()
    }

    private fun setOrientation() {
        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }
}

