package com.example.propertymanagementapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.helpers.TokenManager
import com.example.propertymanagementapp.ui.home.MainActivity

class SplashActivity : AppCompatActivity() {

    //2000 milliseconds = 2 seconds
    private var delayedTime: Long = 2000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
        }, delayedTime)
    }
}