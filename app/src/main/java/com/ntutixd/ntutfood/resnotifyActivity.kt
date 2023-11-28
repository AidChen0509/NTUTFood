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
import com.ntutixd.ntutfood.data.notify_img
import java.util.*
import kotlin.collections.ArrayList

class resnotifyActivity : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<notify_img>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resnotify)

        newRecyclerView = findViewById(R.id.notify_view)
        newRecyclerView.layoutManager = LinearLayoutManager(this)

        newArrayList = arrayListOf<notify_img>()
        val buttomnav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        buttomnav.selectedItemId = R.id.nav_notify
        val radius = resources.getDimension(R.dimen.bottom_nav_radius)
        val bottomBarBackground = buttomnav.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground.shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(CornerFamily.ROUNDED, radius)
            .setTopLeftCorner(CornerFamily.ROUNDED, radius)
            .build()
        getimgurl()
        buttomnav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    val intent = Intent (this,MainActivity::class.java)
                    startActivity(intent)
                    finish();
                }
                R.id.nav_heart -> {
                    val intent = Intent (this,resfavoriteActivity::class.java)
                    startActivity(intent)
                    finish();
                }
                R.id.nav_notify -> {
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

    private fun getimgurl(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("notifydata")
        val notiAdapter = notifyAdapter()
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children){
                    val url = ds.child("url").getValue(String::class.java)
                    newArrayList.add(notify_img(url.toString()))
                }
                notiAdapter.notifyList = newArrayList
                newRecyclerView.adapter = notiAdapter
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
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