package com.tondz.thehocsinhdientu.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tondz.thehocsinhdientu.Api.ApiService
import com.tondz.thehocsinhdientu.Models.FaceID
import com.tondz.thehocsinhdientu.R
import com.tondz.thehocsinhdientu.SQLiteDatabase.DBHelper
import com.tondz.thehocsinhdientu.Utils.Common
import kotlinx.android.synthetic.main.activity_change_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {
    lateinit var dbHelper: DBHelper
    lateinit var faceID:FaceID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        Common.setStatusBarColor(this)
        dbHelper= DBHelper(this@ChangePasswordActivity)
        faceID =  dbHelper.faceID
        btn_back.setOnClickListener({
            finish()
        })
        btn_change_password.setOnClickListener({
            changePassword()
        })
    }
    private fun changePassword(){
        val oldPassword = edt_old_password.text.toString()
        val newPassword = edt_new_password.text.toString()
        val preNewPassword = edt_pre_new_password.text.toString()
        if(oldPassword.equals("")||newPassword.equals("")||preNewPassword.equals("")){
            Common.notiDialog("Thông báo","Không được để trống",this@ChangePasswordActivity)
        }else{
            if(newPassword.equals(preNewPassword)){
                var hashMap = HashMap<String,String>()
                hashMap.put("oldPassword",oldPassword)
                hashMap.put("NewPassword",newPassword)

                hashMap.put("token",Common.TOKEN)
                ApiService.api.changePassword(hashMap)?.enqueue(object: Callback<Void?>{
                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                        Log.e("changepass",response.toString() )
                        if(response.code()==200){
                            if(faceID!=null){
                                faceID.password = newPassword
                                dbHelper.updateFace(faceID)
                            }

                            Common.notiDialog("Thông báo","Đổi mật khẩu thành công",this@ChangePasswordActivity)
                        }else{
                            Common.notiDialog("Thông báo","Mật khẩu hiện tại không đúng",this@ChangePasswordActivity)
                        }
                    }

                    override fun onFailure(call: Call<Void?>, t: Throwable) {
                       Common.notiDialog("Thông báo","Lỗi kết nối",this@ChangePasswordActivity)
                        Log.e("changepass",t.message.toString() )
                    }


                })
            }else{
                Common.notiDialog("Thông báo","Nhập lại mật khẩu không khớp",this@ChangePasswordActivity)
            }
        }
    }
}