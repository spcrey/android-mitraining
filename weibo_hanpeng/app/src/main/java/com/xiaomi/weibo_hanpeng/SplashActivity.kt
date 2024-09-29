package com.xiaomi.weibo_hanpeng

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val displayTime = 1000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val logoStart = findViewById<ImageView>(R.id.ic_logo)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.splash_animation);
        logoStart.startAnimation(fadeIn);
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                delay(displayTime)
                withContext(Dispatchers.Main) {
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }
}