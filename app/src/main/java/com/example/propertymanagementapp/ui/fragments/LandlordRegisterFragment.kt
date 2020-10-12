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
import com.example.propertymanagementapp.databinding.FragmentLandlordRegisterBinding
import com.example.propertymanagementapp.helpers.d
import com.example.propertymanagementapp.ui.auth.AuthListener
import com.example.propertymanagementapp.ui.auth.AuthViewModelRegister
import com.example.propertymanagementapp.ui.auth.LoginActivity
import kotlinx.android.synthetic.main.fragment_landlord_register.view.*

class LandlordRegisterFragment : Fragment(), AuthListener {
    lateinit var layout: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLandlordRegisterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_landlord_register, container, false)
        val viewModel = ViewModelProviders.of(this).get(AuthViewModelRegister::class.java)
        binding.viewModelRegister = viewModel
        viewModel.authListener = this
        // Inflate the layout for this fragment
        layout = binding.root
        init()
        return layout
    }

    private fun init() {
        layout.to_login_screen_text_button_ll.setOnClickListener {

            startActivity(Intent(activity, LoginActivity::class.java))

        }
    }

    override fun onStarted() {
        context?.d("LandlordRegisterFragment", "Fragment Started")
    }

    override fun onSuccess(response: LiveData<String>) {
        response.observe(this, Observer {
            context?.d("LandlordRegisterFragment", "Success! Observer attached to response")
            //startActivity(Intent(activity, LoginActivity::class.java))
        })
        //startActivity(Intent(activity, LoginActivity::class.java))
    }

    override fun failure(message: String) {
        context?.d("LandlordRegisterFragment FAIL", message)
    }

}