package com.tondz.thehocsinhdientu.Activities;

import static com.tondz.thehocsinhdientu.Utils.Common.checkEmpty;
import static com.tondz.thehocsinhdientu.Utils.Common.notiDialog;
import static com.tondz.thehocsinhdientu.Utils.Common.setStatusBarColor;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.tondz.thehocsinhdientu.Api.ApiService;
import com.tondz.thehocsinhdientu.Models.FaceID;
import com.tondz.thehocsinhdientu.Models.LoginModel;
import com.tondz.thehocsinhdientu.R;
import com.tondz.thehocsinhdientu.SQLiteDatabase.DBHelper;
import com.tondz.thehocsinhdientu.Utils.Common;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edtUsername, edtPassword;
    ImageButton btnFaceID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DBHelper db = new DBHelper(getApplicationContext());
        if (db.getFaceID() != null) {
            Log.e("hihi", db.getFaceID().toString());
        } else {
            Log.e("hihi", "null");
        }
        checkPermission();
        initView();
        onClick();
        setStatusBarColor(this);

    }

    private void checkPermission() {
        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 123);
    }

    private void initView() {
        btnLogin = findViewById(R.id.btn_login);
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnFaceID = findViewById(R.id.btn_faceid);
    }

    private void onClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if (checkEmpty(username) && checkEmpty(password)) {
                    //login
                    login(username, password);
                } else {
                    notiDialog("Thông báo", "Tài khoản hoặc mật khẩu không được bỏ trống", LoginActivity.this);
                }

            }
        });
        btnFaceID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int check = checkFace();
                if (check == 2) {
                    startActivity(new Intent(getApplicationContext(), LoginWithFaceIDActivity.class));
                } else {
                    Common.notiDialog("Thông báo", "Chưa có khuôn mặt, vui lòng cập nhật khuôn mặt", getApplicationContext());
                }
            }
        });
    }

    private int checkFace() {
        FaceID faceID = new DBHelper(getApplicationContext()).getFaceID();
        if (faceID != null) {
            if (faceID.getStatus() == 1) {
                return 2;
            }
            return 1;
        }
        return 0;
    }

    private void login(String id, String password) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Đang đăng nhập");
        dialog.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("password", password);
        ApiService.Companion.getApi().Login(map).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.code() == 200) {
                    Common.TOKEN = response.body().getToken();
                    Common.ID = id;
                    Common.PASSWORD = password;
                    if (response.body().getHocSinh() != null) {
                        Log.e("Login", response.body().getHocSinh().getHoTen()
                                + "");
                        Common.HOC_SINH = response.body().getHocSinh();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        dialog.dismiss();
                    }
                } else {
                    Common.notiDialog("Thông báo", "Tài khoản hoặc mật khẩu không chính xác", LoginActivity.this);
                    dialog.dismiss();
                }
                Log.e("Login", response.toString());
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.e("Login", t.toString());
                Common.notiDialog("Thông báo", "Kết nối internet thất bại", LoginActivity.this);
                dialog.dismiss();
            }
        });
    }
}