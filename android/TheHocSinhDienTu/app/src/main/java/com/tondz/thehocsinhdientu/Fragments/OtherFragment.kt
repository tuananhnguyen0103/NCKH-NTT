package com.tondz.thehocsinhdientu.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.tondz.thehocsinhdientu.Activities.ChangePasswordActivity
import com.tondz.thehocsinhdientu.Activities.InformationActivity
import com.tondz.thehocsinhdientu.Activities.LoginActivity
import com.tondz.thehocsinhdientu.Activities.UpdateFaceActivity
import com.tondz.thehocsinhdientu.R
import com.tondz.thehocsinhdientu.Utils.Common
import kotlinx.android.synthetic.main.fragment_other.*

class OtherFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
        loadDataUser()
    }

    private fun onClick() {
        btn_update_face.setOnClickListener({
            startActivity(Intent(context, UpdateFaceActivity::class.java))
        })
        btn_infor.setOnClickListener({
            startActivity(Intent(context, InformationActivity::class.java))
        })
        btn_logout.setOnClickListener({
            activity?.finish()
            startActivity(Intent(context, LoginActivity::class.java))
        })
        btn_change_password.setOnClickListener({
            startActivity(Intent(context,ChangePasswordActivity::class.java))
        })
    }

    private fun loadDataUser() {
        if (Common.HOC_SINH != null) {
            tv_name.setText(Common.HOC_SINH.hoTen)
            Log.e("AnhThe", Common.HOC_SINH.anhThe)
            context?.let {
                Glide.with(it).load(Common.HOC_SINH.anhThe)
                    .error(R.drawable.user).into(img_avatar)
            }
            tv_date_of_birth.text = Common.HOC_SINH.ngaySinh
            tv_phone.text = Common.HOC_SINH.sdt
        }
    }
}