package com.tondz.thehocsinhdientu.Activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.tondz.thehocsinhdientu.R
import com.tondz.thehocsinhdientu.Utils.Common
import kotlinx.android.synthetic.main.activity_noti.*

class NotiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noti)
        Common.setStatusBarColor(this)
        loadData()
        onClick()
    }
    private fun loadData(){
        tv_titile.setText(Common.THONG_BAO.tieuDe)
        tv_content.setText(Common.THONG_BAO.noiDung)
        Glide.with(applicationContext).load(Common.THONG_BAO.hinhAnh).into(img_content)
        if(Common.THONG_BAO.created_date_time!=null){
            tv_date.setText(Common.THONG_BAO.created_date_time)
        }
    }
    private fun  onClick(){
        btn_back.setOnClickListener {
            finish()
        }
        tv_link.setOnClickListener{
            if(Common.THONG_BAO.lienKet!=null){
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(Common.THONG_BAO.lienKet))
                startActivity(intent)
            }
        }
    }
}