package com.hackyeah.app.ui.main

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.lottie.LottieAnimationView
import com.hackyeah.app.R
import com.hackyeah.app.databinding.ActivityMainBinding
import com.hackyeah.app.ui.base.BaseActivity


class MainActivity : BaseActivity() {

    var hudVisible = false
    var hudLoading = false
    var hudHidePostpone = false

    private val animationLayout: ConstraintLayout by lazy {
        findViewById(R.id.layout_loader)
    }

    private val lottieAnimation: LottieAnimationView by lazy {
        findViewById(R.id.lottie_loader_animation)
    }

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun inject() {
        // do nothing
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        showHUD()
        val navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigation.setupWithNavController(navController)
    }

    fun hideBottomNavigation(){
        binding.bottomNavigation.visibility = View.GONE
    }

    fun showBottomNavigation(){
        binding.bottomNavigation.visibility = View.VISIBLE
    }

    fun showHUD() {
        if (hudLoading || hudVisible) {
            return
        }
        hudLoading = true
        lottieAnimation.playAnimation()
        animationLayout.alpha = 0f
        animationLayout.visibility = View.VISIBLE
        animationLayout.animate()?.apply {
            interpolator = LinearInterpolator()
            duration = 200
            alpha(1f)
            setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                    animationLayout.clearAnimation()
                }

                override fun onAnimationEnd(p0: Animator?) {
                    hudVisible = true
                    if (hudHidePostpone) {
                        hideHUD()
                    }
                }

                override fun onAnimationCancel(p0: Animator?) {}

                override fun onAnimationRepeat(p0: Animator?) {}

            })
            startDelay = 0
            start()
        }
    }

    fun hideHUD() {
        if (hudLoading && !hudHidePostpone) {
            hudHidePostpone = true
        }

        if (!hudVisible) {
            return
        }

        animationLayout.animate()?.apply {
            interpolator = LinearInterpolator()
            duration = 200
            alpha(0f)
            startDelay = 200
            setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                    animationLayout.clearAnimation()
                }

                override fun onAnimationEnd(p0: Animator?) {
                    lottieAnimation.pauseAnimation()
                    animationLayout.visibility = View.GONE
                    hudVisible = false
                    hudHidePostpone = false
                    hudLoading = false
                }

                override fun onAnimationCancel(p0: Animator?) {}

                override fun onAnimationRepeat(p0: Animator?) {}
            })
            start()
        }
    }

    companion object {
        fun intentFor(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

}