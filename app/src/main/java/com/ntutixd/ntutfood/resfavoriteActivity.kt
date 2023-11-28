package com.ntutixd.ntutfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ntutixd.ntutfood.data.Feature
import com.ntutixd.ntutfood.data.notify_img
import com.ntutixd.ntutfood.data.respayment
import java.util.*
import kotlin.collections.ArrayList

class resfavoriteActivity : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Feature>
    private lateinit var newArrayList_pay: ArrayList<respayment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resfavorite)

        newRecyclerView = findViewById(R.id.favo_view)
        newRecyclerView.layoutManager = LinearLayoutManager(this)

        newArrayList = arrayListOf<Feature>()
        newArrayList_pay = arrayListOf<respayment>()
        val buttomnav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        buttomnav.selectedItemId = R.id.nav_heart
        val radius = resources.getDimension(R.dimen.bottom_nav_radius)
        val bottomBarBackground = buttomnav.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground.shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(CornerFamily.ROUNDED, radius)
            .setTopLeftCorner(CornerFamily.ROUNDED, radius)
            .build()
        getfavo()
            buttomnav.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_home -> {
                        val intent = Intent (this,MainActivity::class.java)
                        startActivity(intent)
                        finish();
                    }
                    R.id.nav_heart -> {
                    }
                    R.id.nav_notify -> {
                        val intent = Intent (this,resnotifyActivity::class.java)
                        startActivity(intent)
                        finish();
                    }
                    R.id.nav_map -> {
                        val intent = Intent (this,resmapActivity::class.java)
                        startActivity(intent)
                        finish();
                    }
                }
                return@setOnItemSelectedListener true
            }
    }
    private fun getfavo() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("favores")
        val myRef_pay = database.getReference("respayment")
        val favoAdapter = MainAdapter()
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val name = ds.child("name").getValue(String::class.java)
                    val loc = ds.child("loc").getValue(String::class.java)
                    val features = ds.child("features").getValue(String::class.java)
                    val gettime = ds.child("gettime").getValue(String::class.java)
                    val price = ds.child("price").getValue(String::class.java)
                    val rank = ds.child("rank").getValue(Float::class.java)
                    val imgurl = ds.child("url").getValue(String::class.java)
                    val time = ds.child("time").getValue(String::class.java)
                    newArrayList.add(
                        Feature(
                            name.toString(),
                            loc.toString(),
                            features.toString(),
                            gettime.toString(),
                            price.toString(),
                            rank.toString(),
                            imgurl.toString(),
                            time.toString()
                        )
                    )
                }
                favoAdapter.pharmacyList = newArrayList
                newRecyclerView.adapter = favoAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
        myRef_pay.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val cash = ds.child("cashpay").getValue(Boolean::class.java)
                    val credit = ds.child("creditpay").getValue(Boolean::class.java)
                    val easycard = ds.child("easycardpay").getValue(Boolean::class.java)
                    val fiveticket = ds.child("fiveticketpay").getValue(Boolean::class.java)
                    val phonepay = ds.child("phonepay").getValue(Boolean::class.java)
                    val imgurl = ds.child("resdetailimg").getValue(String::class.java)
                    newArrayList_pay.add(
                        respayment(
                            cash!!,
                            credit!!,
                            easycard!!,
                            fiveticket!!,
                            phonepay!!,
                            imgurl.toString()
                        )
                    )
                }
                favoAdapter.pharmacyList_2 = newArrayList_pay
                newRecyclerView.adapter = favoAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }
    private var isExit = false
    private var hasTask = false
    var timerExit = Timer()
    var task: TimerTask = object : TimerTask() {
        override fun run() {
            isExit = false
            hasTask = true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // 判斷是否按下Back
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 是否要退出
            if (isExit == false) {
                isExit = true //記錄下一次要退出
                Toast.makeText(
                    this, "再按一次Back退出APP", Toast.LENGTH_SHORT
                ).show()

                // 如果超過兩秒則恢復預設值
                if (!hasTask) {
                    timerExit.schedule(task, 2000)
                }
            } else {
                finish() // 離開程式
                System.exit(0)
            }
        }
        return false
    }
}