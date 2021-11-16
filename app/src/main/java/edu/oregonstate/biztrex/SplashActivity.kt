package edu.oregonstate.biztrex

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import edu.oregonstate.biztrex.databinding.ActivitySplashBinding

/**
 * Animated splash screen
 */
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private var isAnimationDone = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val zoom: Animation = AnimationUtils.loadAnimation(this, R.anim.zoom)
        binding.imageViewSplash.startAnimation(zoom)

        zoom.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                isAnimationDone = true
                Thread.sleep(2000)
                binding.imageViewSplash.visibility = View.GONE
                binding.splashProgressBar.visibility = View.VISIBLE
                proceed()
            }

            override fun onAnimationRepeat(animation: Animation) {
            }
        })
    }

    private fun proceed()
    {
        if (!isAnimationDone)
            return

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }, 1500L)

        isAnimationDone = false
    }
}