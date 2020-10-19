package com.example.propertymanagementapp.ui.activities

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.adapters.TaskListAdapter
import com.example.propertymanagementapp.data.model.Task
import com.example.propertymanagementapp.helpers.TokenManager
import com.example.propertymanagementapp.ui.auth.RegisterActivity
import com.example.propertymanagementapp.ui.home.HomeActivity
import com.example.propertymanagementapp.ui.home.MainActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.android.synthetic.main.app_tool_bar.*

class TodoActivity : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference
    var mList: ArrayList<Task> = ArrayList()
    var keysList: ArrayList<String> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {
        //gets an instance of our firebase db
        databaseReference = FirebaseDatabase.getInstance().getReference(TokenManager().getUserId())
        // checking to see if the user is logged in
        //if not we take them to the register screen first
        if (!TokenManager().isLoggedIn()) {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        //otherwise continue
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        init()
    }

    override fun onRestart() {
        getData()
        super.onRestart()
    }

    override fun onResume() {
        getData()
        super.onResume()
    }

    private fun init() {
        toolbar()
        getData()


        add_task.setOnClickListener {
            startActivity(Intent(this, NewTaskActivity::class.java))
        }
    }

    private fun getData(){
        var adapter = TaskListAdapter(this, mList)
        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mList.clear()
                keysList.clear()
                for(data in dataSnapshot.children){
                    var user = data.getValue(Task::class.java)
                    var key = data.key
                    mList.add(user!!)
                    keysList.add(key!!)
                }
                users_RV.adapter = adapter
                users_RV.layoutManager = LinearLayoutManager(MainActivity.mContext)
                adapter.setData(mList, keysList)
                progress_bar_main.visibility = View.GONE
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("TodoActivi onCancelled", p0.toString())
            }

        })
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

        toolbar.title = "Tasks"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}