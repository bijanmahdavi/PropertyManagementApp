package com.example.propertymanagementapp.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.propertymanagementapp.R
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {
    lateinit var tabAdapter: TabAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    private fun init() {
        tabAdapter = TabAdapter(supportFragmentManager)
        view_pager.adapter = tabAdapter
        tab_layout.setupWithViewPager(view_pager)
    }

}

/*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.databinding.ActivityRegisterBinding
import com.example.propertymanagementapp.helpers.d
import com.example.propertymanagementapp.helpers.toast

class RegisterActivity : AppCompatActivity(), AuthListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        val viewModel = ViewModelProviders.of(this).get(AuthViewModelRegister::class.java)
        binding.viewModelRegister = viewModel
        viewModel.authListener = this
    }

    override fun onStarted() {
        //this.toast("OnStarted")
        this.d("LoginActivity","onStarted")

    }

    override fun onSuccess(response: LiveData<String>) {
        response.observe(this, Observer {
            this.d("LoginActivity", "Success! Observer attached to response")
            this.toast("success")
        })
    }

    override fun failure(message: String) {
        this.d("LoginActivity","Login Failed")
        this.toast(message)
    }


}*/
