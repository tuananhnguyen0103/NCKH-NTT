package com.tondz.thehocsinhdientu.Activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tondz.thehocsinhdientu.Adapters.ScoreAdapter
import com.tondz.thehocsinhdientu.Api.ApiService
import com.tondz.thehocsinhdientu.Models.Diem
import com.tondz.thehocsinhdientu.R
import com.tondz.thehocsinhdientu.Utils.Common
import kotlinx.android.synthetic.main.activity_score_table.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScoreTableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_table)
        Common.setStatusBarColor(this)
        scoreList = ArrayList()
        scoreAdapter = ScoreAdapter(scoreList, applicationContext)
        rv_score.adapter = scoreAdapter
        onClick()
        loadData()
    }

    private fun onClick() {
        btn_back.setOnClickListener {
            finish()
        }
    }

    private lateinit var scoreList: ArrayList<Diem>
    private lateinit var scoreAdapter: ScoreAdapter
    private fun loadData() {
        ApiService.api.getAllScore(Common.TOKEN).enqueue(object : Callback<List<Diem>> {
            override fun onResponse(call: Call<List<Diem>>, response: Response<List<Diem>>) {
                if (response.code() == 200) {
                    for (diem in response.body()!!) {
                        scoreList.add(diem)
                        scoreAdapter.notifyDataSetChanged()
                        Log.e("SCORE",diem.hoten)
                    }
                }else{
                    Common.notiDialog("Thông báo", "Lỗi", this@ScoreTableActivity)
                }
            }

            override fun onFailure(call: Call<List<Diem>>, t: Throwable) {
                Common.notiDialog("Thông báo", "Lỗi kết nối", this@ScoreTableActivity)
            }

        })
    }
}