package com.example.propertymanagementapp.ui.home

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.adapters.PropertyListAdapter
import com.example.propertymanagementapp.data.model.Property
import com.example.propertymanagementapp.data.repositories.PropertyRepository
import com.example.propertymanagementapp.helpers.TokenManager
import com.example.propertymanagementapp.ui.activities.AddPropertyActivity
import kotlinx.android.synthetic.main.activity_property.*
import kotlinx.android.synthetic.main.app_tool_bar.*

var mPropList: ArrayList<Property> = ArrayList()
var mLivePropList: LiveData<ArrayList<Property>>? = null
lateinit var adapter: PropertyListAdapter

class PropertyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property)
        adapter = PropertyListAdapter(this, mPropList)
        rv_property.adapter = adapter
        rv_property.layoutManager = LinearLayoutManager(this)
        adapter.setData(mPropList)
        init()
    }

    private fun init() {
        toolbar()
        getData()
        button_to_add_property.setOnClickListener {
            startActivity(Intent(this, AddPropertyActivity::class.java))
        }

/*        get_prop_button.setOnClickListener {
            getData()

        }*/
    }

    private fun getData() {
        //mLivePropList = PropertyRepository().getProperties()
        progress_bar_property.visibility = View.VISIBLE
        mLivePropList = PropertyRepository().getPropertiesById(TokenManager().getUserId())
        mLivePropList?.observe(this, object:Observer<ArrayList<Property>> {
            override fun onChanged(t: ArrayList<Property>?) {
                adapter.setData(t!!)
                progress_bar_property.visibility = View.GONE
            }

        })
        //progress_bar_property.visibility = View.GONE
    }

    private fun toolbar() {
        var toolbar = toolbar

        toolbar.title = "Properties"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }


}