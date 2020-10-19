package com.example.propertymanagementapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.data.model.Property
import com.squareup.picasso.Picasso
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

            var link = "https://insidelatinamerica.net/wp-content/uploads/2018/01/noImg_2.jpg"
            var amount = "$0"
            var country = "Canada"
            var state = "Ontario"
            var address = "42 Wallaby Way"

            if(!data.image.isNullOrBlank()) {
                link = data.image
            }
            if(!data.address.isNullOrBlank()) {
                address = data.address
            }
            if(!data.purchasePrice.isNullOrBlank()) {
                amount = data.purchasePrice
            }
            if(!data.country.isNullOrBlank()) {
                country = data.country
            }
            if(!data.state.isNullOrBlank()) {
                state = data.state
            }
            itemView.setOnClickListener {
                //@TODO open property detail activity

            }
            Picasso.get()
                .load(link)
                .resize(100, 100)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(itemView.image_view_property_list)
            itemView.text_view_property_address.text = address
            itemView.text_view_property_country.text = country+" - "
            itemView.text_view_property_state.text = state
            itemView.text_view_property_price.text = amount+" / Month"

        }
    }


}