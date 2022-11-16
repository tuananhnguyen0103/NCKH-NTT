package com.tondz.thehocsinhdientu.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tondz.thehocsinhdientu.Activities.NotiActivity;
import com.tondz.thehocsinhdientu.Models.ThongBao;
import com.tondz.thehocsinhdientu.R;
import com.tondz.thehocsinhdientu.Utils.Common;

import java.util.List;

public class NotiAdapter extends RecyclerView.Adapter<NotiAdapter.ViewHolder> {
    Context context;
    List<ThongBao> thongBaoList;

    public NotiAdapter(Context context, List<ThongBao> thongBaoList) {
        this.context = context;
        this.thongBaoList = thongBaoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThongBao thongBao = thongBaoList.get(position);
        if (thongBao.getTieuDe().length() > 50) {
            holder.tvTittile.setText(thongBao.getTieuDe().substring(0, 44) + "...");
        } else {
            holder.tvTittile.setText(thongBao.getTieuDe());
        }
        if (thongBao.getNoiDung().length() > 150) {
            holder.tvContent.setText(thongBao.getNoiDung().substring(0, 120) + "...");
        } else {
            holder.tvContent.setText(thongBao.getNoiDung());
        }
        holder.tvDate.setText(thongBao.getNgayThongBao());
        Glide.with(context).load(thongBao.getHinhAnh()).into(holder.imgContent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.THONG_BAO = thongBao;
                context.startActivity(new Intent(context, NotiActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return thongBaoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent, tvTittile, tvDate;
        ImageView imgContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvTittile = itemView.findViewById(R.id.tv_titile);
            imgContent = itemView.findViewById(R.id.img_content);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }
}
