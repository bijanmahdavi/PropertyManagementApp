package com.example.propertymanagementapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.data.model.Task
import com.example.propertymanagementapp.helpers.TokenManager
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_new_task.*

class NewTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        init()
    }

    private fun init() {
        button_insert.setOnClickListener {
            var title = edit_text_task_description.text.toString()
            var description = edit_text_task_title.text.toString()
            var task = Task(description, title)


            var databaseReference = FirebaseDatabase.getInstance().getReference(TokenManager().getUserId())

            // insert blank record to generate unique id and save it in local variable
            var userId = databaseReference.push().key

            databaseReference.child(userId!!).setValue(task)
            Toast.makeText(applicationContext, "inserted", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, TodoActivity::class.java))

        }
    }
}