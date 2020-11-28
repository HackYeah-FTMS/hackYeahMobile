package com.hackyeah.app.ui.splash

import android.os.Bundle
import android.os.Handler
import com.hackyeah.app.databinding.ActivitySplashBinding
import com.hackyeah.app.ui.base.BaseActivity
import com.hackyeah.app.ui.main.MainActivity

class SplashActivity : BaseActivity() {

    private val SPLASH_SCREEN_TIME_OUT = 2000L

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    override fun inject() {
        // Do nothing
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler()
            .postDelayed(
                {
                    startActivity(
                        MainActivity.intentFor(this)
                    )

                    finish()
                }, SPLASH_SCREEN_TIME_OUT
            )
    }
}