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
import com.tondz.thehocsinhdientu.Activities.NewsActivity;
import com.tondz.thehocsinhdientu.Models.News;
import com.tondz.thehocsinhdientu.R;
import com.tondz.thehocsinhdientu.Utils.Common;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context context;
    private List<News> newsList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_news, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = newsList.get(position);
        if (news.getTitile().length() > 45) {
            holder.tvContent.setText(news.getTitile().substring(0, 44) + "...");
        } else {
            holder.tvContent.setText(news.getTitile());
        }

        Glide.with(context).load(news.getImage()).into(holder.imgContent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.NEWS = news;
                context.startActivity(new Intent(context, NewsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public NewsAdapter(Context context, List<News> newsList) {

        this.context = context;
        this.newsList = newsList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgContent;
        private TextView tvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgContent = itemView.findViewById(R.id.img_content);
            tvContent = itemView.findViewById(R.id.tv_titile);
        }
    }
}
