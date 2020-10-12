package com.example.propertymanagementapp.ui.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.helpers.TokenManager
import com.example.propertymanagementapp.ui.auth.LoginActivity
import com.example.propertymanagementapp.ui.auth.RegisterActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this.applicationContext
        if(TokenManager().isLoggedIn()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_main)
        mContext = this.applicationContext

        init()
    }

    private fun init() {
        main_login_button.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        main_register_button.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    companion object {
        lateinit var mContext: Context
    }
}