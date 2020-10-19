package com.example.propertymanagementapp.ui.home

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.helpers.TokenManager
import com.example.propertymanagementapp.helpers.toast
import com.example.propertymanagementapp.ui.activities.TodoActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_tool_bar.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }

    private fun init() {
        toolbar()
        properties.setOnClickListener {
            startActivity(Intent(this, PropertyActivity::class.java))
        }
        todo.setOnClickListener {
            startActivity(Intent(this, TodoActivity::class.java))
        }

        alerts.setOnClickListener {
            this.toast("alerts")
        }

        tenants.setOnClickListener {
            this.toast("tenants")
        }

        transactions.setOnClickListener {
            this.toast("transactions")
        }

        collect_rent.setOnClickListener {
            this.toast("collect rent")
        }

        trips.setOnClickListener {
            this.toast("trips")
        }

        documents.setOnClickListener {
            this.toast("documents")
        }

        vendors.setOnClickListener {
            this.toast("vendors")
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
        toolbar.title = "Home"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            dialogLogout()
        }
    }
}