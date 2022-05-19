package com.ssafy.gumipresso.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kakao.sdk.common.util.Utility
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.common.ApplicationClass.Companion.userPrefs
import com.ssafy.gumipresso.common.CONST
import com.ssafy.gumipresso.databinding.ActivityLoginBinding
import com.ssafy.gumipresso.fragment.JoinFragment
import com.ssafy.gumipresso.fragment.LoginFragment

private const val TAG ="LoginActivity"
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = userPrefs.getString("User","")
        if(!user.equals("")){
            Toast.makeText(this, "${user}님 자동 로그인 되었습니다.", Toast.LENGTH_SHORT).show()
            //movePage(CONST.ACTIVITY_MAIN)
        }
        Log.d(TAG, "onCreate: ${Utility.getKeyHash(this)}")
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