package com.ssafy.gumipresso_admin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ssafy.gumipresso_admin.R
import com.ssafy.gumipresso_admin.common.CONST
import com.ssafy.gumipresso_admin.databinding.ActivityLoginBinding
import com.ssafy.gumipresso_admin.fragment.JoinFragment
import com.ssafy.gumipresso_admin.fragment.LoginFragment

private const val TAG ="LoginActivity"
class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_login, LoginFragment()).commit()
    }

    fun movePage(page : CONST) {
        when(page){
            CONST.ACTIVITY_MAIN -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                Log.d(TAG, "movePage: $intent")
                startActivity(intent)
                finish()
            }
            CONST.FRAG_LOGIN ->{
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left,R.anim.exit_to_right)
                    .replace(R.id.fragment_container_login, LoginFragment()).commit()
            }
            CONST.FRAG_JOIN ->{
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left,R.anim.exit_to_right)
                    .replace(R.id.fragment_container_login, JoinFragment()).addToBackStack(null).commit()
            }
        }
    }
}