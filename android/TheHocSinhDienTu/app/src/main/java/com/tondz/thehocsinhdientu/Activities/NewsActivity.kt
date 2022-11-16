package com.tondz.thehocsinhdientu.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.tondz.thehocsinhdientu.R
import com.tondz.thehocsinhdientu.Utils.Common
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        Common.setStatusBarColor(this)
        loadData()
        btn_back.setOnClickListener {
            finish()
        }
    }

    fun loadData() {
        Glide.with(applicationContext).load(Common.NEWS.image).into(img_content)
        tv_content.setText(Common.NEWS.content)
        tv_titile.setText(Common.NEWS.titile)
    }
}