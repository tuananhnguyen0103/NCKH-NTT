package com.tondz.thehocsinhdientu.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tondz.thehocsinhdientu.Adapters.NotiAdapter;
import com.tondz.thehocsinhdientu.Models.ThongBao;
import com.tondz.thehocsinhdientu.R;

import java.util.ArrayList;
import java.util.List;


public class NotiFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_noti, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        loadNoti();
    }

    RecyclerView rv_noti;
    NotiAdapter notiAdapter;
    List<ThongBao> thongBaoList = new ArrayList<>();

    private void initView(View view) {
        rv_noti = view.findViewById(R.id.rv_notification);
//        notiAdapter = new NotiAdapter(getContext(), thongBaoList);
//        rv_noti.setAdapter(notiAdapter);
    }

    private void loadNoti() {
        thongBaoList.add(new ThongBao("Căn cứ vào luật của nhà trường, do  vi phạm quá nhiều thứ, như hút ma tuý trong trường, lôi kéo bạn bè đánh bạc, đánh nhau nhà trường quyết định đuỏi học", "Thông báo về việc đuổi vĩnh viễn học sinh Nguyễn Tuấn Anh", "https://cdn.canhco.net/files/2020/09/jh-1.png","12/2/2022"));
        thongBaoList.add(new ThongBao("Căn cứ vào luật của nhà trường, do  vi phạm quá nhiều thứ, như hút ma tuý trong trường, lôi kéo bạn bè đánh bạc, đánh nhau nhà trường quyết định đuỏi học", "Thông báo về việc đuổi vĩnh viễn học sinh Nguyễn Tuấn Anh", "https://cdn.canhco.net/files/2020/09/jh-1.png","12/2/2022"));
        thongBaoList.add(new ThongBao("Căn cứ vào luật của nhà trường, do  vi phạm quá nhiều thứ, như hút ma tuý trong trường, lôi kéo bạn bè đánh bạc, đánh nhau nhà trường quyết định đuỏi học", "Thông báo về việc đuổi vĩnh viễn học sinh Nguyễn Tuấn Anh", "https://cdn.canhco.net/files/2020/09/jh-1.png","12/2/2022"));
        thongBaoList.add(new ThongBao("Căn cứ vào luật của nhà trường, do  vi phạm quá nhiều thứ, như hút ma tuý trong trường, lôi kéo bạn bè đánh bạc, đánh nhau nhà trường quyết định đuỏi học", "Thông báo về việc đuổi vĩnh viễn học sinh Nguyễn Tuấn Anh", "https://cdn.canhco.net/files/2020/09/jh-1.png","12/2/2022"));
        thongBaoList.add(new ThongBao("Căn cứ vào luật của nhà trường, do  vi phạm quá nhiều thứ, như hút ma tuý trong trường, lôi kéo bạn bè đánh bạc, đánh nhau nhà trường quyết định đuỏi học", "Thông báo về việc đuổi vĩnh viễn học sinh Nguyễn Tuấn Anh", "https://cdn.canhco.net/files/2020/09/jh-1.png","12/2/2022"));
        thongBaoList.add(new ThongBao("Căn cứ vào luật của nhà trường, do  vi phạm quá nhiều thứ, như hút ma tuý trong trường, lôi kéo bạn bè đánh bạc, đánh nhau nhà trường quyết định đuỏi học", "Thông báo về việc đuổi vĩnh viễn học sinh Nguyễn Tuấn Anh", "https://cdn.canhco.net/files/2020/09/jh-1.png","12/2/2022"));
        notiAdapter = new NotiAdapter(getContext(), thongBaoList);
        rv_noti.setAdapter(notiAdapter);

    }
}