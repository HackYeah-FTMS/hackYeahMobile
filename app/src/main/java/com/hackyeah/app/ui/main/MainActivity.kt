package com.hackyeah.app.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.hackyeah.app.R
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

        val navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigation.setupWithNavController(navController)
    }

    fun hideBottomNavigation(){
        binding.bottomNavigation.visibility = View.GONE
    }

    fun showBottomNavigation(){
        binding.bottomNavigation.visibility = View.VISIBLE
    }

    companion object {
        fun intentFor(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

}