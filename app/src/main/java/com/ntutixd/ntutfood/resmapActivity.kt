package com.ntutixd.ntutfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.squareup.picasso.Picasso
import java.util.*

class resmapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resmap)
        val mapimg = findViewById<ImageView>(R.id.map_img)
        val imgurl = "https://i.imgur.com/uWfxKQw.png"
        Picasso.get().load(imgurl).into(mapimg)
        val buttomnav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        buttomnav.selectedItemId = R.id.nav_map
        val radius = resources.getDimension(R.dimen.bottom_nav_radius)
        val bottomBarBackground = buttomnav.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground.shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(CornerFamily.ROUNDED, radius)
            .setTopLeftCorner(CornerFamily.ROUNDED, radius)
            .build()
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
                    val intent = Intent (this,resnotifyActivity::class.java)
                    startActivity(intent)
                    finish();
                }
                R.id.nav_map -> {
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