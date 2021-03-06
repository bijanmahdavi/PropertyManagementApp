package com.example.propertymanagementapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.databinding.ActivityLoginBinding

import com.example.propertymanagementapp.helpers.d
import com.example.propertymanagementapp.helpers.toast
import com.example.propertymanagementapp.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.authListener = this
        init()
    }

    private fun init() {
        progress_bar_login.visibility = View.INVISIBLE
        to_register_screen_text_button.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        button_login.setOnClickListener {
            progress_bar_login.visibility = View.VISIBLE
        }
    }

    override fun onStarted() {
        this.d("LoginActivity","onStarted")

    }

    override fun onSuccess(response: LiveData<String>) {
        response.observe(this, Observer {
            this.d("LoginActivity", "Success! Observer attached to response")
            this.toast("success")
            //store token
            this.d("login response", response.toString())
            startActivity(Intent(this, HomeActivity::class.java))
        })
    }

    override fun failure(message: String) {
        this.d("LoginActivity","Login Failed")
        this.toast(message)
    }


}