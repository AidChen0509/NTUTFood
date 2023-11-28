package com.ntutixd.ntutfood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ntutixd.ntutfood.data.notify_img
import com.squareup.picasso.Picasso

class notifyAdapter : RecyclerView.Adapter<notifyAdapter.MyViewHolder>() {
    var notifyList: List<notify_img> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemViewBinding = LayoutInflater.from(parent.context).inflate(R.layout.notify_item_view,parent,false)
        return MyViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(notifyList[position].url).into(holder.notifyimg)
    }

    override fun getItemCount(): Int {
        return notifyList.size
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val notifyimg : ImageView = itemView.findViewById(R.id.notify_img)
    }
}