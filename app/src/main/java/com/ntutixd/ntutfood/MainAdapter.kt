package com.ntutixd.ntutfood


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ntutixd.ntutfood.data.Feature
import com.ntutixd.ntutfood.data.respayment
import com.ntutixd.ntutfood.databinding.ItemViewBinding
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import java.net.HttpURLConnection
import java.net.URL


class MainAdapter : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {
    var pharmacyList: List<Feature> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var pharmacyList_2: List<respayment> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemViewBinding = LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        return MyViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val context=holder.itemView.context
        Picasso.get().load(pharmacyList[position].imgurl).into(holder.ntutcardimg)
        holder.tvName.text = pharmacyList[position].name
        holder.locfeat.text = pharmacyList[position].loc+"  •  "+pharmacyList[position].features
        holder.timepricerank.text = pharmacyList[position].gettime+"  •  "+pharmacyList[position].price+"  •  "+"☆"+pharmacyList[position].rank
        holder.rescardview.cardElevation = 0F
        holder.clickcard.setOnClickListener {
            val intent = Intent (it.context,resdetailActivity::class.java)
            intent.putExtra("name",pharmacyList[position].name)
            intent.putExtra("loc",pharmacyList[position].loc)
            intent.putExtra("features",pharmacyList[position].features)
            intent.putExtra("gettime",pharmacyList[position].gettime)
            intent.putExtra("time",pharmacyList[position].time)
            intent.putExtra("price",pharmacyList[position].price)
            intent.putExtra("rank",pharmacyList[position].rank)
            intent.putExtra("cashpay",pharmacyList_2[position].cashpay)
            intent.putExtra("creditpay",pharmacyList_2[position].creditpay)
            intent.putExtra("easycardpay",pharmacyList_2[position].easycardpay)
            intent.putExtra("fiveticketpay",pharmacyList_2[position].fiveticketpay)
            intent.putExtra("phonepay",pharmacyList_2[position].phonepay)
            intent.putExtra("url",pharmacyList_2[position].resdetailimg)
            context.startActivity(intent)
        }
        holder.clickcard.isLongClickable
        holder.clickcard.setOnLongClickListener {
            Toast.makeText(it.context, "此餐廳已加入您的最愛餐廳列表", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return pharmacyList.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tvName : TextView = itemView.findViewById(R.id.tv_name)
        val locfeat : TextView = itemView.findViewById(R.id.locfeat)
        val timepricerank : TextView = itemView.findViewById(R.id.timepricerank)
        val rescardview : CardView = itemView.findViewById(R.id.rescardview)
        val ntutcardimg : ImageView = itemView.findViewById(R.id.ntutcardimg)
        val clickcard : ConstraintLayout = itemView.findViewById(R.id.clickcard)
    }

}