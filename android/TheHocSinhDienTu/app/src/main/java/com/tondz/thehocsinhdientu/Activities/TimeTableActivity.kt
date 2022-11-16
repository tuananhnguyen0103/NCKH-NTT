package com.tondz.thehocsinhdientu.Activities

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tondz.thehocsinhdientu.Adapters.TimeTableAdapter
import com.tondz.thehocsinhdientu.Api.ApiService
import com.tondz.thehocsinhdientu.Models.ThoiKhoaBieu
import com.tondz.thehocsinhdientu.R
import com.tondz.thehocsinhdientu.Utils.Common
import kotlinx.android.synthetic.main.activity_time_table.*
import retrofit2.Call
import retrofit2.Response

class TimeTableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)
        Common.setStatusBarColor(this)
        onClick()
        loadData()
    }

    var thoiKhoaBieuList = ArrayList<ThoiKhoaBieu>()
    lateinit var timeTableAdapter: TimeTableAdapter
    private fun loadData() {
        var progressDialog = ProgressDialog(this@TimeTableActivity)
        progressDialog.setTitle("Vui lòng chờ")
        progressDialog.show()
        timeTableAdapter = TimeTableAdapter(this@TimeTableActivity, thoiKhoaBieuList)
        rv_time_table.adapter = timeTableAdapter
        ApiService.api.getAllTimeTable(Common.TOKEN)
            .enqueue(object : retrofit2.Callback<List<ThoiKhoaBieu>> {
                override fun onResponse(
                    call: Call<List<ThoiKhoaBieu>>,
                    response: Response<List<ThoiKhoaBieu>>
                ) {
                    if (response.code() == 200) {
                        for (thoiKhoaBieu in response.body()!!) {
                            thoiKhoaBieuList.add(thoiKhoaBieu)
                            timeTableAdapter.notifyDataSetChanged()
                        }
                        progressDialog.dismiss()

                    } else {
                        Toast.makeText(
                            this@TimeTableActivity,
                            "Không có thời khóa biểu",
                            Toast.LENGTH_LONG
                        ).show()
                        progressDialog.dismiss()
                    }
                }

                override fun onFailure(call: Call<List<ThoiKhoaBieu>>, t: Throwable) {
                    Toast.makeText(this@TimeTableActivity, "Lỗi kết nối", Toast.LENGTH_LONG).show()
                    progressDialog.dismiss()
                }

            })
    }

    private fun onClick() {
        btn_back.setOnClickListener({
            finish()
        })

    }
}