package com.example.propertymanagementapp.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.data.model.Task
import com.example.propertymanagementapp.helpers.TokenManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.task_list.view.*

class TaskListAdapter(var mContext: Context, var mList: ArrayList<Task>) : RecyclerView.Adapter<TaskListAdapter.MyViewHolder>() {

    lateinit var databaseReference: DatabaseReference
    lateinit var keysList: ArrayList<String>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListAdapter.MyViewHolder {
        databaseReference = FirebaseDatabase.getInstance().getReference(Task.COLLECTION_NAME)
        var view = LayoutInflater.from(mContext).inflate(R.layout.task_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskListAdapter.MyViewHolder, position: Int) {
        holder.bind(mList[position], position)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(lst: ArrayList<Task>, kLst: ArrayList<String>) {
        mList = lst
        keysList = kLst
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Task, position: Int) {
            Log.d("Data", data.toString())
            itemView.username_text_view.setText(data.title)
            itemView.email_text_view.setText(data.description)
            itemView.complete.isChecked = data.status!!
            if(itemView.complete.isChecked) {
                itemView.setBackgroundColor(Color.GREEN)
            }


            itemView.button_to_delete.setOnClickListener {
                var databaseReference = FirebaseDatabase.getInstance().getReference(TokenManager().getUserId())

                Log.d("DBR", keysList[position])
                databaseReference.child(keysList[position]).setValue(null)
            }

            itemView.button_to_update.setOnClickListener {
                val task = Task(itemView.username_text_view.text.toString(),itemView.email_text_view.text.toString())
                var databaseReference = FirebaseDatabase.getInstance().getReference(TokenManager().getUserId())
                var item = databaseReference.child(keysList[position])
                item.setValue(task)
            }
            itemView.complete.setOnClickListener {
                var color = itemView.background
                if(!itemView.complete.isChecked) {
                    Log.d("Code", "Being run")
                    val db = FirebaseDatabase.getInstance().getReference(TokenManager().getUserId())
                    db.child(keysList[position]).setValue((Task(data.title, data.description, true)))
                    itemView.complete.isChecked = true
                    data.status = true
                    itemView.setBackgroundColor(Color.GREEN)
                }
            }
        }
    }
}