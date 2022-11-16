package com.tondz.thehocsinhdientu.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.tondz.thehocsinhdientu.Activities.DetailScoresActivity
import com.tondz.thehocsinhdientu.Models.Diem
import com.tondz.thehocsinhdientu.R
import com.tondz.thehocsinhdientu.Utils.Common

class ScoreAdapter : RecyclerView.Adapter<ScoreAdapter.ViewHolder> {
    private var scoreList: ArrayList<Diem>
    private var context: Context

    constructor(scoreList: ArrayList<Diem>, context: Context) : super() {
        this.scoreList = scoreList
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_score, parent, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder != null) {
            val score = scoreList[position]
            val animationDuration = 2500 // 2500ms = 2,5s
            holder.circularProgressBar.setProgressWithAnimation(
                score.tbm.toFloat() / 10 * 100,
                animationDuration
            ) // Default duration = 1500ms
            holder.tvKiHoc.setText("Kì học: " + score.kihoc)
            holder.tvNamHoc.setText("Năm học: " + score.namhoc + "-" + (score.namhoc + 1))
            holder.tvScore.setText(score.tbm.toString())
            holder.itemView.setOnClickListener({
                Common.DIEM = score
                val intent = Intent(context,DetailScoresActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            })
        }
    }

    override fun getItemCount(): Int {
        return scoreList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var circularProgressBar: CircularProgressBar = itemView!!.findViewById(R.id.cicular)
        var tvScore: TextView = itemView!!.findViewById(R.id.tv_score)
        var tvNamHoc: TextView = itemView!!.findViewById(R.id.tv_namhoc)
        var tvKiHoc: TextView = itemView!!.findViewById(R.id.tv_hocki)
    }
}