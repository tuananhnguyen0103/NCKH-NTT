package com.tondz.thehocsinhdientu.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.barteksc.pdfviewer.PDFView;
import com.tondz.thehocsinhdientu.Api.ApiService;
import com.tondz.thehocsinhdientu.Models.ThoiKhoaBieu;
import com.tondz.thehocsinhdientu.R;
import com.tondz.thehocsinhdientu.Utils.Common;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        loadPdfView();
    }

    PDFView pdfView;
    TextView tv_start, tv_end;

    private void initView(View view) {
        pdfView = view.findViewById(R.id.pdf_view);
        tv_start = view.findViewById(R.id.tv_time_start);
        tv_end = view.findViewById(R.id.tv_time_end);
    }

    private void loadPdfView() {
//        ApiService.Companion.getApi().getTimeTable(Common.TOKEN).enqueue(new Callback<ThoiKhoaBieu>() {
//            @Override
//            public void onResponse(Call<ThoiKhoaBieu> call, Response<ThoiKhoaBieu> response) {
//                if (response.code() == 201) {
//                    ThoiKhoaBieu thoiKhoaBieu = response.body();
//                    new RetrivePDFfromUrl().execute(thoiKhoaBieu.getDuongDan());
//                    tv_start.setText(String.valueOf(thoiKhoaBieu.getNgayBatDau()));
//                    tv_end.setText(String.valueOf(thoiKhoaBieu.getNgayKetThuc()));
//                } else {
//                    new RetrivePDFfromUrl().execute("https://www.calendarpedia.co.uk/download/timetable/timetable-monday-to-friday-in-colour.pdf");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ThoiKhoaBieu> call, Throwable t) {
//                new RetrivePDFfromUrl().execute("https://www.calendarpedia.co.uk/download/timetable/timetable-monday-to-friday-in-colour.pdf");
//            }
//        });

    }

    class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            // we are using inputstream
            // for getting out PDF.
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                //if url have https cast ->HttpsURLConnection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            pdfView.fromStream(inputStream).load();
        }
    }

}