package com.example.propertymanagementapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.databinding.FragmentTenantRegisterBinding
import com.example.propertymanagementapp.helpers.d
import com.example.propertymanagementapp.ui.auth.AuthListener
import com.example.propertymanagementapp.ui.auth.AuthViewModelRegister
import com.example.propertymanagementapp.ui.auth.LoginActivity
import com.example.propertymanagementapp.ui.auth.RegisterActivity
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_landlord_register.view.*
import kotlinx.android.synthetic.main.fragment_tenant_register.view.*

class TenantRegisterFragment : Fragment(), AuthListener {
    lateinit var layout: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTenantRegisterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tenant_register, container, false)
        //.setContentView(RegisterActivity(), R.layout.activity_register)
        val viewModel = ViewModelProviders.of(this).get(AuthViewModelRegister::class.java)
        binding.viewModelRegister = viewModel
        viewModel.authListener = this
        // Inflate the layout for this fragment
        layout = binding.root
        init()
        return layout
    }

    private fun init() {
        layout.to_login_screen_text_button_ten.setOnClickListener {

            startActivity(Intent(activity, LoginActivity::class.java))

        }
    }

    override fun onStarted() {
        context?.d("TenantRegisterFragment", "Fragment Started")
    }

    override fun onSuccess(response: LiveData<String>) {
        response.observe(this, Observer {
            context?.d("TenantRegisterFragment", "Success! Observer attached to response")
        })
    }

    override fun failure(message: String) {
        context?.d("TenantRegisterFragment FAIL", message)
    }


}