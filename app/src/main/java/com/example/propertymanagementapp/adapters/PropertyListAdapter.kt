package com.example.propertymanagementapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.data.model.Property
import kotlinx.android.synthetic.main.property_list.view.*

class PropertyListAdapter(var mContext: Context, var mList: ArrayList<Property>) : RecyclerView.Adapter<PropertyListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.property_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mList[position], position)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(lst: ArrayList<Property>) {
        mList = lst
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Property, position: Int) {
            itemView.setOnClickListener {
                //@TODO open property detail activity
                Log.d("Item clicked", itemView.text_view_property_id.text.toString())
            }
            itemView.text_view_property_id.text = data._id

        }
    }


}