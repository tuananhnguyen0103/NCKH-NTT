package com.tondz.thehocsinhdientu.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tondz.thehocsinhdientu.R
import com.tondz.thehocsinhdientu.Utils.Common
import com.tondz.thehocsinhdientu.Utils.Common.DIEM
import kotlinx.android.synthetic.main.activity_detail_scores.*

class DetailScoresActivity : AppCompatActivity() {
    val ANIMATION_DURATION = 2500 // 2500ms = 2,5s
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_scores)
        Common.setStatusBarColor(this)
        loadData()
        btn_back.setOnClickListener({
            finish()
        })
    }

    private fun loadData() {
        if (DIEM != null) {
            if (DIEM.isTheduc == 1) {
                tv_score_theduc.text = "Đạt"
            } else {
                tv_score_theduc.text = "Trượt"
            }
            cicular_score_theduc.setProgressWithAnimation(100f, ANIMATION_DURATION)

            cicular_score_toan.setProgressWithAnimation(
                DIEM.toan.toFloat() / 10 * 100, ANIMATION_DURATION
            )
            tv_score_toan.text = DIEM.toan.toString()


            cicular_score_vatli.setProgressWithAnimation(
                DIEM.vatli.toFloat() / 10 * 100, ANIMATION_DURATION
            )
            tv_score_vatli.text = DIEM.vatli.toString()



            cicular_score_hoahoc.setProgressWithAnimation(
                DIEM.hoahoc.toFloat() / 10 * 100, ANIMATION_DURATION
            )
            tv_score_hoahoc.text = DIEM.hoahoc.toString()


            cicular_score_sinhhoc.setProgressWithAnimation(
                DIEM.sinhhoc.toFloat() / 10 * 100, ANIMATION_DURATION
            )
            tv_score_sinhhoc.text = DIEM.sinhhoc.toString()



            cicular_score_tinhoc.setProgressWithAnimation(
                DIEM.tinhoc.toFloat() / 10 * 100, ANIMATION_DURATION
            )
            tv_score_tinhoc.text = DIEM.tinhoc.toString()



            cicular_score_nguvan.setProgressWithAnimation(
                DIEM.nguvan.toFloat() / 10 * 100, ANIMATION_DURATION
            )
            tv_score_nguvan.text = DIEM.nguvan.toString()



            cicular_score_lichsu.setProgressWithAnimation(
                DIEM.lichsu.toFloat() / 10 * 100, ANIMATION_DURATION
            )
            tv_score_lichsu.text = DIEM.lichsu.toString()


            cicular_score_dialy.setProgressWithAnimation(
                DIEM.diali.toFloat() / 10 * 100, ANIMATION_DURATION
            )
            tv_score_diali.text = DIEM.diali.toString()


            cicular_score_ngoaingu.setProgressWithAnimation(
                DIEM.ngoaingu1.toFloat() / 10 * 100, ANIMATION_DURATION
            )
            tv_score_ngoaingu.text = DIEM.ngoaingu1.toString()


            cicular_score_congnghe.setProgressWithAnimation(
                DIEM.congnghe.toFloat() / 10 * 100, ANIMATION_DURATION
            )
            tv_score_congnghe.text = DIEM.congnghe.toString()

            cicular_score_qpan.setProgressWithAnimation(
                DIEM.ghqp.toFloat() / 10 * 100, ANIMATION_DURATION
            )
            tv_score_qpan.text = DIEM.ghqp.toString()


            cicular_score_gdcd.setProgressWithAnimation(
                DIEM.gdcd.toFloat() / 10 * 100, ANIMATION_DURATION
            )
            tv_score_gdcd.text = DIEM.gdcd.toString()

        }
    }
}