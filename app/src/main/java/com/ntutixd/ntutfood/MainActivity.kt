package com.ntutixd.ntutfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.ntutixd.ntutfood.data.PharmacyInfo
import com.ntutixd.ntutfood.databinding.ActivityMainBinding
import java.sql.Array
import com.ntutixd.ntutfood.data.Feature
import com.ntutixd.ntutfood.data.respayment
import com.google.android.material.shape.CornerFamily

import com.google.android.material.shape.MaterialShapeDrawable


import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    //定義全域變數
    private lateinit var viewAdapter: MainAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var binding: ActivityMainBinding
    private lateinit var restaurantdata:ArrayList<Feature>
    private lateinit var newArrayList: ArrayList<respayment>
    private lateinit var tempArrayList: ArrayList<Feature>
    private lateinit var cheapList: ArrayList<Feature>
    private lateinit var fastList: ArrayList<Feature>
    private lateinit var ranktopList: ArrayList<Feature>
    private lateinit var temp2ArrayList: ArrayList<Feature>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        getPharmacyData()

    }

    private fun initView() {
        // 定義 LayoutManager 為 LinearLayoutManager
        viewManager = LinearLayoutManager(this)

        // 自定義 Adapte 為 MainAdapter，稍後再定義 MainAdapter 這個類別
        viewAdapter = MainAdapter()

        // 定義從佈局當中，拿到 recycler_view 元件，
        binding.recyclerView.apply {
            // 透過 kotlin 的 apply 語法糖，設定 LayoutManager 和 Adapter
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun getPharmacyData() {
        val radius = resources.getDimension(R.dimen.bottom_nav_radius)
        val bottomAppBar = findViewById<BottomNavigationView>(R.id.bottom_nav)

        val bottomBarBackground = bottomAppBar.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground.shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(CornerFamily.ROUNDED, radius)
            .setTopLeftCorner(CornerFamily.ROUNDED, radius)
            .build()
        //顯示忙碌圈圈
        binding.progressBar.visibility = View.VISIBLE
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("resdata")
        val myRef_pay = database.getReference("respayment")
        restaurantdata = arrayListOf<Feature>()
        newArrayList = arrayListOf<respayment>()
        tempArrayList = arrayListOf<Feature>()
        temp2ArrayList = arrayListOf<Feature>()
        cheapList = arrayListOf<Feature>()
        fastList = arrayListOf<Feature>()
        ranktopList = arrayListOf<Feature>()
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //for (ds in dataSnapshot.children) {
                    //val name = ds.child("name").getValue(String::class.java)
                    //val time = ds.child("time").getValue(String::class.java)
                    //val loc = ds.child("loc").getValue(String::class.java)
                    //val features = ds.child("features").getValue(String::class.java)
                    //Log.d("TAG", "$name / $time/$loc/$features")
                //}
                for (ds in dataSnapshot.children){
                    val name = ds.child("name").getValue(String::class.java)
                    val loc = ds.child("loc").getValue(String::class.java)
                    val features = ds.child("features").getValue(String::class.java)
                    val gettime = ds.child("gettime").getValue(String::class.java)
                    val price = ds.child("price").getValue(String::class.java)
                    val rank = ds.child("rank").getValue(Float::class.java)
                    val imgurl = ds.child("url").getValue(String::class.java)
                    val time = ds.child("time").getValue(String::class.java)
                    val ranktop = ds.child("ranktop").getValue(String::class.java)
                    val cheap = ds.child("cheap").getValue(String::class.java)
                    val fast = ds.child("fast").getValue(String::class.java)
                    if (ranktop=="1"){
                        ranktopList.add(Feature(name.toString(),loc.toString(),features.toString(),gettime.toString(),price.toString(),rank.toString(),imgurl.toString(),time.toString()))
                    }
                    if (cheap=="1"){
                        cheapList.add(Feature(name.toString(),loc.toString(),features.toString(),gettime.toString(),price.toString(),rank.toString(),imgurl.toString(),time.toString()))
                    }
                    if (fast=="1"){
                        fastList.add(Feature(name.toString(),loc.toString(),features.toString(),gettime.toString(),price.toString(),rank.toString(),imgurl.toString(),time.toString()))
                    }
                    restaurantdata.add(Feature(name.toString(),loc.toString(),features.toString(),gettime.toString(),price.toString(),rank.toString(),imgurl.toString(),time.toString()))
                }
                tempArrayList.addAll(restaurantdata)
                viewAdapter.pharmacyList = tempArrayList
                runOnUiThread {
                    //將下載的資料，指定給 MainAdapter


                    //關閉忙碌圈圈
                    binding.progressBar.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
        //搜尋
        val searchview = findViewById<androidx.appcompat.widget.SearchView>(R.id.home_search_bar)
        searchview.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tempArrayList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()){
                    restaurantdata.forEach {
                        if (it.name.lowercase(Locale.getDefault()).contains(searchText)){
                            tempArrayList.add(it)
                        }
                        if (it.features.lowercase(Locale.getDefault()).contains(searchText)){
                            tempArrayList.add(it)
                        }
                    }
                    viewAdapter.pharmacyList = tempArrayList
                }
                else
                {
                    tempArrayList.clear()
                    tempArrayList.addAll(restaurantdata)
                    viewAdapter.pharmacyList = tempArrayList
                }
                return false
            }

        })
        myRef_pay.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children){
                    val cash = ds.child("cashpay").getValue(Boolean::class.java)
                    val credit = ds.child("creditpay").getValue(Boolean::class.java)
                    val easycard = ds.child("easycardpay").getValue(Boolean::class.java)
                    val fiveticket = ds.child("fiveticketpay").getValue(Boolean::class.java)
                    val phonepay = ds.child("phonepay").getValue(Boolean::class.java)
                    val imgurl = ds.child("resdetailimg").getValue(String::class.java)
                    newArrayList.add(respayment(cash!!,credit!!,easycard!!,fiveticket!!,phonepay!!,imgurl.toString()))
                }
                viewAdapter.pharmacyList_2 = newArrayList
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
        val homebutton_1 = findViewById<ImageButton>(R.id.home_button_1)
        var counter_1 = 2
        var counter_2 = 2
        var counter_3 = 2
        homebutton_1.setOnClickListener {
            counter_1=counter_1+1
            if (counter_1 % 2 == 0){
                homebutton_1.setImageResource(R.drawable.ic_hb1)
                counter_1 = 2
                temp2ArrayList.removeAll(cheapList)
                viewAdapter.pharmacyList=temp2ArrayList
                if (counter_2==2&&counter_3==2){
                    viewAdapter.pharmacyList=tempArrayList
                }
            }else{
                homebutton_1.setImageResource(R.drawable.ic_hb1_selected)
                temp2ArrayList.addAll(cheapList)
                viewAdapter.pharmacyList=temp2ArrayList
            }
        }
        val homebutton_2 = findViewById<ImageButton>(R.id.home_button_2)
        homebutton_2.setOnClickListener {
            counter_2=counter_2+1
            if (counter_2 % 2 == 0){
                homebutton_2.setImageResource(R.drawable.ic_hb2)
                temp2ArrayList.removeAll(fastList)
                viewAdapter.pharmacyList=temp2ArrayList
                counter_2 = 2
                if (counter_1==2&&counter_3==2){
                    viewAdapter.pharmacyList=tempArrayList
                }
            }else{
                homebutton_2.setImageResource(R.drawable.ic_hb2_selected)
                temp2ArrayList.addAll(fastList)
                viewAdapter.pharmacyList=temp2ArrayList
            }
        }
        val homebutton_3 = findViewById<ImageButton>(R.id.home_button_3)
        homebutton_3.setOnClickListener {
            counter_3=counter_3+1
            if (counter_3 % 2 == 0){
                homebutton_3.setImageResource(R.drawable.ic_hb3)
                temp2ArrayList.removeAll(ranktopList)
                viewAdapter.pharmacyList=temp2ArrayList
                counter_3 = 2
                if (counter_1==2&&counter_2==2){
                    viewAdapter.pharmacyList=tempArrayList
                }
            }else{
                homebutton_3.setImageResource(R.drawable.ic_hb3_selected)
                temp2ArrayList.addAll(ranktopList)
                viewAdapter.pharmacyList=temp2ArrayList
            }
        }
    bottomAppBar.setOnItemSelectedListener {
        when (it.itemId) {
            R.id.nav_home -> {

            }
            R.id.nav_heart -> {
                val intent = Intent (binding.root.context,resfavoriteActivity::class.java)
                startActivity(intent)
                finish();
            }
            R.id.nav_notify -> {
                val intent = Intent (binding.root.context,resnotifyActivity::class.java)
                startActivity(intent)
                finish();
            }
            R.id.nav_map -> {
                val intent = Intent (binding.root.context,resmapActivity::class.java)
                startActivity(intent)
                finish();
            }
        }
        return@setOnItemSelectedListener true
    }
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