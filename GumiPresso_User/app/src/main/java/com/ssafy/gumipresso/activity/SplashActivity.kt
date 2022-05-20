package com.ssafy.gumipresso.activity

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity() {
    private lateinit var animationDrawable: AnimationDrawable
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            // 공유 이미지 애니메이션
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                binding.ivLogo,
                "imgTransition"
            )
            startActivity(intent, options.toBundle())
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish()
        }, 1500)

        animationDrawable = binding.ivLogo.getBackground() as AnimationDrawable
        animationDrawable.start()
    }
}