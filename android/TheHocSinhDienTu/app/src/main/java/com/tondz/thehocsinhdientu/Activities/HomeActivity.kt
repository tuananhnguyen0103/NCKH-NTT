package com.tondz.thehocsinhdientu.Activities

import android.Manifest
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tondz.thehocsinhdientu.Adapters.HomeAdapter
import com.tondz.thehocsinhdientu.Models.FaceID
import com.tondz.thehocsinhdientu.Models.SchoolLocation
import com.tondz.thehocsinhdientu.R
import com.tondz.thehocsinhdientu.SQLiteDatabase.DBHelper
import com.tondz.thehocsinhdientu.Utils.Common
import com.tondz.thehocsinhdientu.ml.Facenet
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException

class HomeActivity : AppCompatActivity() {
    var viewPager: ViewPager? = null
    var navBottom: BottomNavigationView? = null
    var dbHelper: DBHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
        navBottom!!.itemIconTintList = null
        Common.setStatusBarColor(this)
        setViewPager()
        setNavBottom()
        setupFaceDetector()
        instanceModel()
        //startActivity(new Intent(getApplicationContext(), UploadActivity.class));
        dbHelper = DBHelper(this@HomeActivity)
        Log.e("SIZE", dbHelper!!.all.size.toString())
        if (dbHelper!!.faceID != null) {
            if (!dbHelper!!.faceID.idHocSinh.toString().equals(Common.ID.toString())) {
                if (Common.HOC_SINH.anh1 != "" && Common.HOC_SINH.anh1 != null) {
                    noti()
                }
            }
        } else {

            if (Common.HOC_SINH.anh1 != "" && Common.HOC_SINH.anh1 != null) {
                noti()
            }
        }
        val handler = Handler()
        val runnable: Runnable = object : Runnable {
            override fun run() {
                getCurrentLocation()
                handler.postDelayed(this, 30000)
            }
        }
        handler.post(runnable)

    }

    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private fun getCurrentLocation() {
        ActivityCompat.requestPermissions(
            this@HomeActivity,
            arrayOf<String>(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), 123
        )
        mFusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this@HomeActivity)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mFusedLocationProviderClient.lastLocation.addOnCompleteListener(this@HomeActivity) { task ->
            run {
                val location: Location? = task.result
                if (location != null) {
                    Common.lStudent = SchoolLocation(location.latitude, location.longitude)
                    Log.e("LOCATION", location.latitude.toString())
                    Common.checkLocation()
                } else {
                    Common.notiDialog("Thông báo", "Vui lòng bật vị trí", this@HomeActivity)
                }
            }
        }
    }


    private fun downloadImage(): List<Bitmap> {
        val progressDialog = ProgressDialog(this@HomeActivity)
        progressDialog.setMessage("Đang cập nhật khuôn mặt")
        progressDialog.show()
        var bitmapList = ArrayList<Bitmap>()
        Glide.with(this).asBitmap().load(Common.HOC_SINH.anh1)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmapList.add(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }

            })
        Glide.with(this).asBitmap().load(Common.HOC_SINH.anh2)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmapList.add(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }

            })
        Glide.with(this).asBitmap().load(Common.HOC_SINH.anh3)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmapList.add(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }

            })
        Glide.with(this).asBitmap().load(Common.HOC_SINH.anh4)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmapList.add(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }

            })
        Glide.with(this).asBitmap().load(Common.HOC_SINH.anh5)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmapList.add(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }

            })
        Glide.with(this).asBitmap().load(Common.HOC_SINH.anh6)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmapList.add(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }

            })
        Glide.with(this).asBitmap().load(Common.HOC_SINH.anh7)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmapList.add(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }

            })
        Glide.with(this).asBitmap().load(Common.HOC_SINH.anh8)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmapList.add(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }

            })
        Glide.with(this).asBitmap().load(Common.HOC_SINH.anh9)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmapList.add(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }

            })
        Glide.with(this).asBitmap().load(Common.HOC_SINH.anh10)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmapList.add(resource)
                    Log.e("LISTIMAGE", bitmapList.size.toString())
                    getFeatures(bitmapList)
                    progressDialog.dismiss()
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }

            })

        return ArrayList()
    }

    //get labels
    private fun getFeatures(bitmapList: ArrayList<Bitmap>) {
        Common.PREDICTS = ArrayList()
        for (bitmap in bitmapList) {
            val byteBuffer = Common.convertBitmapToByteBuffer(bitmap)
            inputFeature.loadBuffer(byteBuffer)
            val outputs = model!!.process(inputFeature)
            val outputBuffer = outputs.outputFeature0AsTensorBuffer
            val predict = outputBuffer.floatArray
            val tmp = sort(predict)
            Log.e("predict", predict.size.toString() + "")
            for (i in 0..2) {
                Common.PREDICTS.add(
                    findIndex(
                        outputBuffer.floatArray,
                        tmp!![i]
                    )
                )
                Log.e(
                    "predict",
                    "index: " + findIndex(
                        outputBuffer.floatArray,
                        tmp!![i]
                    ) + " prob:" + predict[i] + ""
                )
            }
        }
        val set: Set<Int> = LinkedHashSet(Common.PREDICTS)
        Common.PREDICTS = ArrayList(set)
        for (i in Common.PREDICTS.indices) {
            Log.e("predict", Common.PREDICTS[i].toString() + "")
        }
        val labels = Common.toString(Common.PREDICTS)
        Log.e("Labels", labels)
        val faceID = FaceID(Common.ID, Common.PASSWORD, labels, 1)
        DBHelper(this@HomeActivity).updateFace(faceID)
        Log.e("PredictSuccess", "Thành công")
        Toast.makeText(applicationContext, "Tải khuôn mặt thành công", Toast.LENGTH_LONG)
            .show()
    }


    lateinit var inputFeature: TensorBuffer
    private fun setupFaceDetector() {
        inputFeature = TensorBuffer.createFixedSize(intArrayOf(1, 160, 160, 3), DataType.FLOAT32)
    }

    private fun sort(arr: FloatArray): FloatArray? {
        val n = arr.size
        for (i in 0 until n - 1) for (j in 0 until n - i - 1) if (arr[j] < arr[j + 1]) {
            // swap arr[j+1] và arr[i]
            val temp = arr[j]
            arr[j] = arr[j + 1]
            arr[j + 1] = temp
        }
        return arr
    }

    private fun findIndex(arr: FloatArray, prob: Float): Int {
        var index = -1
        for (i in arr.indices) {
            if (arr[i] == prob) {
                index = i
            }
        }
        return index
    }

    var model: Facenet? = null

    //instance model
    private fun instanceModel() {
        try {
            model = Facenet.newInstance(this)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun acc(predict: IntArray, prob: FloatArray): Float {
        var count = 0
        for (i in predict.indices) {
            for (integer in Common.PREDICTS) {
                if (predict[i] == integer) {
                    count++
                }
            }
        }
        return count.toFloat() / Common.PREDICTS.size
    }


    private fun noti() {
        val alert = AlertDialog.Builder(this@HomeActivity)
        alert.setTitle("Thông báo")
        alert.setMessage("Bạn có muốn tải về dữ liệu khuôn mặt?")
        alert.setPositiveButton("Có") { dialogInterface, i ->
            dialogInterface.dismiss()
            downloadImage()

        }
        alert.setNegativeButton("Không") { dialogInterface, i -> dialogInterface.dismiss() }
        alert.show()
    }

    private fun init() {
        viewPager = findViewById(R.id.view_pager)
        navBottom = findViewById(R.id.nav_bottom)
    }

    private fun setViewPager() {
        val homeAdapter = HomeAdapter(
            supportFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        viewPager!!.adapter = homeAdapter
        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> navBottom!!.menu.findItem(R.id.item_home).isChecked = true
                    1 -> navBottom!!.menu.findItem(R.id.item_calendar).isChecked = true
                    2 -> navBottom!!.menu.findItem(R.id.item_noti).isChecked = true
                    3 -> navBottom!!.menu.findItem(R.id.item_menu).isChecked = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun setNavBottom() {
        navBottom!!.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_home -> viewPager!!.currentItem = 0
                R.id.item_calendar -> viewPager!!.currentItem = 1
                R.id.item_noti -> viewPager!!.currentItem = 2
                R.id.item_menu -> viewPager!!.currentItem = 3
            }
            true
        }
    }
}