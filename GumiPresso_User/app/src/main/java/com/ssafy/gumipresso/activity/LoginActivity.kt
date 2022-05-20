package com.ssafy.gumipresso.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kakao.sdk.common.util.Utility
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.common.CONST
import com.ssafy.gumipresso.databinding.ActivityLoginBinding
import com.ssafy.gumipresso.fragment.JoinFragment
import com.ssafy.gumipresso.fragment.LoginFragment

private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Log.d(TAG, "onCreate: ${Utility.getKeyHash(this)}")
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_login, LoginFragment()).commit()
    }

    fun movePage(page: CONST) {
        when (page) {
            CONST.ACTIVITY_MAIN -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                Log.d(TAG, "movePage: $intent")
                startActivity(intent)
                overridePendingTransition(R.anim.activity_anim_enter, R.anim.activity_anim_exit);
                finish()
            }
            CONST.FRAG_LOGIN -> {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                    )
                    .replace(R.id.fragment_container_login, LoginFragment()).commit()
            }
            CONST.FRAG_JOIN -> {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                    )
                    .replace(R.id.fragment_container_login, JoinFragment()).addToBackStack(null)
                    .commit()
            }
        }
    }
}