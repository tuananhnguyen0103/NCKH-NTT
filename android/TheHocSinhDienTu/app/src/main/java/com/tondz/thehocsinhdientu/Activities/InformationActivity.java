package com.tondz.thehocsinhdientu.Activities;

import static com.tondz.thehocsinhdientu.Utils.Common.HOC_SINH;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.tondz.thehocsinhdientu.Api.ApiService;
import com.tondz.thehocsinhdientu.R;
import com.tondz.thehocsinhdientu.Utils.Common;
import com.tondz.thehocsinhdientu.Utils.RealPathUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationActivity extends AppCompatActivity {

    ImageView imgAvatar;
    TextView tvName, tvDateOfBirth, tvClass, tvScholastic;
    EditText edtName, edtDate, edtGioiTinh, edtSDT, edtThonXom, edtXa, edtHuyen, edtTinh, edtNoiSinh, edtNoiThuongTru, edtNamNhappHoc, edtNamRaTruong;
    private static final int REQUEST_CODE = 967;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        initView();
        Common.setStatusBarColor(this);
        requestPermionAndPickImage();
        loadUser();
        onClick();

    }

    private void onClick() {
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.btn_change_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    private void requestPermionAndPickImage() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();

                Log.e("UploadAvatar", uri.toString());
                uploadFiles(uri);
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(uri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imgAvatar.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    public void uploadFiles(Uri uri) {
        if (uri == null) return;
        // Hàm call api sẽ mất 1 thời gian nên mình show 1 dialog nhé.
        File file = new File(RealPathUtils.getRealPath(getApplicationContext(),uri));
        // Khởi tạo RequestBody từ file đã được chọn
        RequestBody requestBody = RequestBody.create(
                MediaType.parse(getContentResolver().getType(uri)),
                file);
        // Trong retrofit 2 để upload file ta sử dụng Multipart, khai báo 1 MultipartBody.Part
        // uploaded_file là key mà mình đã định nghĩa trong khi khởi tạo server
        MultipartBody.Part filePart =
                MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        ApiService.Companion.getApi().updateAvatar(Common.TOKEN,filePart).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.e("FileUpload",response.message());
                if(response.code()==201){
                    Log.e("FileUpload","Thành công");
                }else{
                    Log.e("FileUpload","Thất bại");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("FileUpload","Thất bại"+t.getMessage());
            }
        });

    }

    private void loadUser() {
        if (HOC_SINH != null) {
            Glide.with(InformationActivity.this).load(HOC_SINH.getAnhThe()).error(R.drawable.user).into(imgAvatar);
            tvName.setText(HOC_SINH.getHoTen());
            tvScholastic.setText(String.valueOf(HOC_SINH.getNamNhapHoc()));
            tvDateOfBirth.setText(HOC_SINH.getNgaySinh());
            tvClass.setText(String.valueOf(HOC_SINH.getIdLop()));
            edtXa.setText(String.valueOf(HOC_SINH.getQueQuanXa()));
            edtHuyen.setText(String.valueOf(HOC_SINH.getQueQuanHuyen()));
            edtTinh.setText(String.valueOf(HOC_SINH.getQueQuanTinh()));
            edtNoiSinh.setText(String.valueOf(HOC_SINH.getNoiSinh()));
            edtNoiThuongTru.setText(String.valueOf(HOC_SINH.getNoiThuongTru()));
            edtNamNhappHoc.setText(String.valueOf(HOC_SINH.getNamNhapHoc()));
            edtNamRaTruong.setText(String.valueOf(HOC_SINH.getNamRaTruong()));
            edtSDT.setText(String.valueOf(HOC_SINH.getSDT()));
            edtThonXom.setText(String.valueOf(HOC_SINH.getQueQuanThonXom()));
            edtName.setText(String.valueOf(HOC_SINH.getHoTen()));
            edtDate.setText(String.valueOf(HOC_SINH.getNgaySinh()));
            edtGioiTinh.setText(String.valueOf(HOC_SINH.getGioiTinh()));
        }


    }

    private void initView() {
        edtXa = findViewById(R.id.edt_xa);
        edtHuyen = findViewById(R.id.edt_huyen);
        edtTinh = findViewById(R.id.edt_tinh);
        edtNoiSinh = findViewById(R.id.edt_noi_sinh);
        edtNoiThuongTru = findViewById(R.id.edt_noi_thuong_tru);
        edtNamNhappHoc = findViewById(R.id.edt_nam_nhap_hoc);
        edtNamRaTruong = findViewById(R.id.edt_nam_ra_truong);
        edtSDT = findViewById(R.id.edt_phone_number);
        edtThonXom = findViewById(R.id.edt_thon_xom);
        edtName = findViewById(R.id.edt_name);
        edtDate = findViewById(R.id.edt_date_of_birth);
        edtGioiTinh = findViewById(R.id.edt_gioi_tinh);
        imgAvatar = findViewById(R.id.img_avatar);
        tvName = findViewById(R.id.tv_name);
        tvDateOfBirth = findViewById(R.id.tv_date_of_birth);
        tvClass = findViewById(R.id.tv_class);
        tvScholastic = findViewById(R.id.tv_schoolastic);
    }
}