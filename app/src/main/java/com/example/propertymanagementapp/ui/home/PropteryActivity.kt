package com.example.propertymanagementapp.ui.home

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.android.synthetic.main.app_tool_bar.*

var mPropList: ArrayList<Property> = ArrayList()
var mLivePropList: LiveData<ArrayList<Property>>? = null
lateinit var adapter: PropertyListAdapter

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        adapter = PropertyListAdapter(this, mPropList)
        rv_property.adapter = adapter
        rv_property.layoutManager = LinearLayoutManager(this)
        adapter.setData(mPropList)
        init()
    }

    private fun init() {
        toolbar()

        button_to_add_property.setOnClickListener {
            startActivity(Intent(this, AddPropertyActivity::class.java))
        }

        get_prop_button.setOnClickListener {
            //mLivePropList = PropertyRepository().getProperties()
            mLivePropList = PropertyRepository().getPropertiesById(TokenManager().getUserId())
            mLivePropList?.observe(this, object:Observer<ArrayList<Property>> {
                override fun onChanged(t: ArrayList<Property>?) {
                    adapter.setData(t!!)
                }

            })
            //make progress bar invisible here
        }
    }

    private fun dialogLogout() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Log Out")
        builder.setMessage("Are you sure you want to log out?")
        builder.setNegativeButton("No", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, p1: Int) {
                dialog?.dismiss()
            }
        })
        builder.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                TokenManager().logout()
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
        })
        var myAlertDialog = builder.create()
        myAlertDialog.show()
    }

    private fun toolbar() {
        var toolbar = toolbar
        toolbar.setNavigationIcon(R.drawable.ic_baseline_meeting_room_24)
        toolbar.setOnClickListener{
            dialogLogout()
        }
        toolbar.setNavigationOnClickListener {
            dialogLogout()
        }
        toolbar.title = "Properties"
        setSupportActionBar(toolbar)
    }


}