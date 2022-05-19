package com.ssafy.gumipresso_admin.activity

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.ssafy.gumipresso_admin.R

class SplashActivity : AppCompatActivity() {
    private lateinit var animationDrawable: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 1500)

        var imageView = findViewById<View>(R.id.imageview)
        animationDrawable = imageView.getBackground() as AnimationDrawable
        animationDrawable.start()
    }
}