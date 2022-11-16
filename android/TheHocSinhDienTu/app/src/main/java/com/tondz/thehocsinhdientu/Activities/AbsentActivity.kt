package com.tondz.thehocsinhdientu.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tondz.thehocsinhdientu.Api.ApiService
import com.tondz.thehocsinhdientu.R
import com.tondz.thehocsinhdientu.Utils.Common
import kotlinx.android.synthetic.main.activity_absent.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AbsentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_absent)
        Common.setStatusBarColor(this)
        btn_back.setOnClickListener({
            finish()
        })
        btnAbsent.setOnClickListener {
            val reason = edtReason.text.toString()
            if (reason.length <= 10) {
                Common.notiDialog("Thông báo", "Nội dung nghỉ học quá ngắn", this@AbsentActivity)
            } else {
                val hashMap = HashMap<String, String>()
                hashMap.put("token", Common.TOKEN)
                hashMap.put("noiDung", reason)
                ApiService.api.insertDateOff(hashMap)?.enqueue(object:Callback<Void?>{
                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                        if(response.code()==200){
                            Common.notiDialog("Thông báo","Xin nghỉ học thành công, đợi hệ thống gọi xác nhận từ phụ huynh nhé",this@AbsentActivity)
                        }else{
                            Common.notiDialog("Thông báo","Lỗi",this@AbsentActivity)
                        }
                    }

                    override fun onFailure(call: Call<Void?>, t: Throwable) {
                        Common.notiDialog("Thông báo","Lỗi mạng",this@AbsentActivity)
                    }

                })
            }
        }
    }
}