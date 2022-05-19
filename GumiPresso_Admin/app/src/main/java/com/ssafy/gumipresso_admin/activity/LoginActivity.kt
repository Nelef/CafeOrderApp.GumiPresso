package com.ssafy.gumipresso_admin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.gumipresso_admin.R
import com.ssafy.gumipresso_admin.fragment.LoginFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_login, LoginFragment()).commit()
    }
}