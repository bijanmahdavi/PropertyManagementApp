package com.example.propertymanagementapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.data.repositories.PropertyRepository
import kotlinx.android.synthetic.main.activity_add_property.*

class AddPropertyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_property)
        init()
    }

    private fun init() {
        button_add_new_property_save.setOnClickListener {
            PropertyRepository().newProperty(text_input_address.text.toString(), text_input_city.text.toString(), text_input_country.text.toString(), text_input_purchase_price.text.toString(), text_input_state.text.toString())
        }
    }
}