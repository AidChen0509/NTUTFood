package com.ntutixd.ntutfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.squareup.picasso.Picasso

class resdetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resdetail)

        val restitle_view : TextView = findViewById(R.id.restaurant_title)
        val resloc_feat_view : TextView = findViewById(R.id.japenese_su)
        val min_view : TextView = findViewById(R.id.min)
        val money_view : TextView = findViewById(R.id.badge_money)
        val rating_view : TextView = findViewById(R.id.ratings)
        val timedetail_view : TextView = findViewById(R.id.restaurant_timedeatil)
        val cash_view : RelativeLayout = findViewById(R.id.restaurant_left)
        val five_view : RelativeLayout = findViewById(R.id.restaurant_left_2)
        val samsung_view : RelativeLayout = findViewById(R.id.restaurant_left_3)
        val credit_view : RelativeLayout = findViewById(R.id.restaurant_right)
        val easycard_view : RelativeLayout = findViewById(R.id.restaurant_right_2)
        val apple_view : RelativeLayout = findViewById(R.id.restaurant_right_3)
        val big_pic_view : ImageView = findViewById(R.id.resdetail_bigpic)

        val bundle : Bundle?= intent.extras
        val restitle = bundle!!.getString("name")
        val resloc_feat = bundle.getString("loc")+"  •  "+bundle.getString("features")
        val min = bundle.getString("gettime")
        val money = bundle.getString("price")
        val rating = bundle.getString("rank")
        val timedetail = bundle.getString("time")
        val cash = bundle.getBoolean("cashpay")
        val five = bundle.getBoolean("fiveticketpay")
        val phone = bundle.getBoolean("phonepay")
        val credit = bundle.getBoolean("creditpay")
        val easycard = bundle.getBoolean("easycardpay")
        val url = bundle.getString("url")

        restitle_view.text = restitle
        resloc_feat_view.text = resloc_feat
        min_view.text = min
        money_view.text = money
        rating_view.text = rating
        timedetail_view.text = timedetail
        fun cashshowHide(view:View) {
            view.visibility = if (cash){
                View.VISIBLE
            } else{
                View.GONE
            }
        }
        fun creditshowHide(view:View) {
            view.visibility = if (credit){
                View.VISIBLE
            } else{
                View.GONE
            }
        }
        fun easyshowHide(view:View) {
            view.visibility = if (easycard){
                View.VISIBLE
            } else{
                View.GONE
            }
        }
        fun samshowHide(view:View) {
            view.visibility = if (phone){
                View.VISIBLE
            } else{
                View.GONE
            }
        }
        fun fiveshowHide(view:View) {
            view.visibility = if (five){
                View.VISIBLE
            } else{
                View.GONE
            }
        }
        fun appleshowHide(view:View) {
            view.visibility = if (phone){
                View.VISIBLE
            } else{
                View.GONE
            }
        }
        cashshowHide(cash_view)
        creditshowHide(credit_view)
        easyshowHide(easycard_view)
        fiveshowHide(five_view)
        appleshowHide(apple_view)
        samshowHide(samsung_view)

        Picasso.get().load(url).into(big_pic_view)
    }
}