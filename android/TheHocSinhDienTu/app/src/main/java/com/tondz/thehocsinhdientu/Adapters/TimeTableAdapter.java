package com.tondz.thehocsinhdientu.Adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.barteksc.pdfviewer.PDFView;
import com.tondz.thehocsinhdientu.Models.ThoiKhoaBieu;
import com.tondz.thehocsinhdientu.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.ViewHolder> {
    Context context;
    List<ThoiKhoaBieu> thoiKhoaBieuList;

    public TimeTableAdapter(Context context, List<ThoiKhoaBieu> thoiKhoaBieuList) {
        this.context = context;
        this.thoiKhoaBieuList = thoiKhoaBieuList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_time_table, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder != null) {
            ThoiKhoaBieu thoiKhoaBieu = thoiKhoaBieuList.get(position);
            holder.tvTimeStart.setText(String.valueOf(thoiKhoaBieu.getNgayBatDau()));
            holder.tvTimeEnd.setText(String.valueOf(thoiKhoaBieu.getNgayKetThuc()));
            try {
                InputStream inputStream = new RetrivePDFfromUrl().execute(thoiKhoaBieu.getDuongDan()).get();
                holder.pdfView.fromStream(inputStream).load();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setTitle("Vui lòng chờ");
                        progressDialog.show();
                        Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.dialog_time_table);
                        PDFView pdfViewDialog = dialog.findViewById(R.id.dialog_pdf_view);
                        InputStream ip = null;
                        try {
                            ip = new RetrivePDFfromUrl().execute(thoiKhoaBieu.getDuongDan()).get();
                            pdfViewDialog.fromStream(ip).load();
                            progressDialog.dismiss();
                            dialog.show();
                        } catch (ExecutionException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }

                    }
                });
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public int getItemCount() {
        return thoiKhoaBieuList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTimeStart, tvTimeEnd;
        PDFView pdfView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimeStart = itemView.findViewById(R.id.tv_time_start);
            tvTimeEnd = itemView.findViewById(R.id.tv_time_end);
            pdfView = itemView.findViewById(R.id.pdf_view);
        }
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
    }
}
