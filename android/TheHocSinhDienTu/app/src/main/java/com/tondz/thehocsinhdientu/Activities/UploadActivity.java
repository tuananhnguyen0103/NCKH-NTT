package com.tondz.thehocsinhdientu.Activities;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.tondz.thehocsinhdientu.Api.ApiService;
import com.tondz.thehocsinhdientu.R;
import com.tondz.thehocsinhdientu.Utils.Common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadActivity extends AppCompatActivity {

    List<Bitmap> bitmapList;
    List<File> fileList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        bitmapList = new ArrayList<>();
        bitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.test_face_2));
        bitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.test_face_2));
        bitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.test_face_2));
        bitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.test_face_2));
        bitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.test_face_2));
        bitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.test_face_2));
        bitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.test_face_2));
        bitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.test_face_2));
        bitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.test_face_2));
        bitmapList.add(BitmapFactory.decodeResource(getResources(), R.drawable.test_face_2));



    }


}