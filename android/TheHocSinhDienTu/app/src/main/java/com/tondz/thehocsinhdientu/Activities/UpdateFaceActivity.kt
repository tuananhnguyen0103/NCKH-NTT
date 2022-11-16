package com.tondz.thehocsinhdientu.Activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.tondz.thehocsinhdientu.Adapters.FaceAdapter
import com.tondz.thehocsinhdientu.Api.ApiService.Companion.api
import com.tondz.thehocsinhdientu.Models.FaceID
import com.tondz.thehocsinhdientu.R
import com.tondz.thehocsinhdientu.SQLiteDatabase.DBHelper
import com.tondz.thehocsinhdientu.Utils.Common
import com.tondz.thehocsinhdientu.ml.Facenet
import kotlinx.android.synthetic.main.activity_update_face.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.time.LocalDateTime

class UpdateFaceActivity : AppCompatActivity() {

    lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    var bitmapList: ArrayList<Bitmap> = ArrayList()
    lateinit var inputFeature: TensorBuffer
    lateinit var highAccuractiyOpts: FaceDetectorOptions
    lateinit var faceAdapter: FaceAdapter
    lateinit var faceDetector: FaceDetector
    var success = false
    var count_crop = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_face)
        checkPermission()
        Common.setStatusBarColor(this)
        cameraProviderFuture = ProcessCameraProvider.getInstance(applicationContext)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(applicationContext))
        setupFaceDetector()
        instanceModel()
        loadAdapter()
        onClick()

    }


    //upload image
    @RequiresApi(Build.VERSION_CODES.O)
    private fun uploadFile() {
        val progressDialog = ProgressDialog(this@UpdateFaceActivity)
        progressDialog.setMessage("Đang cập nhật khuôn mặt");
        if (!this.isFinishing) {
            progressDialog.show()
        }

        val fileList = ArrayList<File>();
        requestPermionAndPickImage()
        for (bitmap in bitmapList) {
            try {
                val file = Common.savebitmap(
                    applicationContext,
                    bitmap,
                    "face" + bitmapList.indexOf(bitmap) + LocalDateTime.now().toString()
                )
                fileList.add(file)
            } catch (e: Exception) {
                Log.e("CHECKFILE", e.message!!)
            }
        }
        val parts = arrayOfNulls<MultipartBody.Part>(fileList.size)
        for (i in parts.indices) {
            val requestBody = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                fileList[i]
            )
            parts[i] = MultipartBody.Part.createFormData("file", "face$i.png", requestBody)
        }
        api.updateFace(Common.TOKEN, parts)!!.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if (call.isExecuted) {
                    progressDialog.dismiss()
                    Log.e("CHECKFILE", "Check $response")
                    if (response.code() == 201) {
                        Log.e("CHECKFILE", "Thành công")
                        for(file in fileList){
                            if(file.delete()){
                                Log.e("DELETE","success")
                            }else{
                                Log.e("DELETE","failed")
                            }
                        }
                        finish()

                    } else {
                        Log.e("CHECKFILE", "Lỗi")
                        Toast.makeText(this@UpdateFaceActivity, "cập nhật khuôn mặt lỗi", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                Log.e("CHECKFILE", "Lỗi " + t.message)
                Toast.makeText(this@UpdateFaceActivity, "cập nhật khuôn mặt lỗi", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun requestPermionAndPickImage() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            123
        )
    }


    //set adapter for recycleview
    private fun loadAdapter() {
        faceAdapter = FaceAdapter(bitmapList, applicationContext)
        rv_face.adapter = faceAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onClick() {
        btn_cap.setOnClickListener {
            val handler = Handler()
            var runnable: Runnable = object : Runnable {
                override fun run() {
                    val bitmap = preview_camera.bitmap
                    if (bitmap != null) {
                        cropFace(bitmap)
                        count_crop++
                        progress_circular.progress = bitmapList.size * 10f;
                    }
                    handler.postDelayed(this, 1000)

                }
            }
            handler.post(runnable)
            btn_cap.visibility = View.INVISIBLE
        }
        btn_back.setOnClickListener({
            finish()
        })
    }

    private fun cropFace(bitmap: Bitmap) {

        if (bitmap != null) {
            val image = InputImage.fromBitmap(bitmap!!, 0)
            faceDetector.process(image).addOnSuccessListener({ faces ->
                for (face in faces) {
                    val bounds = face.boundingBox
                    val left = bounds.left.toFloat()
                    val right = bounds.right.toFloat()
                    val top = bounds.top.toFloat()
                    val bottom = bounds.bottom.toFloat()
                    if (left < 0 || top < 0 || bottom > bitmap.getHeight() || right > bitmap.getWidth() || bitmap.getWidth() <= 0 || bitmap.getHeight() <= 0) {
                        tv_noti.text = "Chưa nhận diện được khuôn mặt"
                    } else {
                        if (right - left <= 0 || bottom - top <= 0) {
                            tv_noti.text = "Chưa nhận diện được khuôn mặt"
                        } else {
                            var faceCrop = Bitmap.createBitmap(
                                bitmap,
                                left.toInt(),
                                top.toInt(),
                                (right - left).toInt(),
                                (bottom - top).toInt()
                            )
                            bitmapList.add(faceCrop)
                            faceAdapter.notifyDataSetChanged()
                            tv_noti.text = "Giữ nguyên camera"
                        }
                    }
                    if (bitmapList.size == 10) {
                        tv_noti.text = "Đang kiểm tra khuôn mặt"
                        getFeatures()

                    }
                    break
                }
            })
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun duDoan(bitmap: Bitmap) {
        val byteBuffer =
            Common.convertBitmapToByteBuffer(bitmap)
        inputFeature.loadBuffer(byteBuffer)
        val outputs = model!!.process(inputFeature)
        val outputBuffer = outputs.outputFeature0AsTensorBuffer
        val predict = outputBuffer.floatArray
        val tmp = sort(predict)
        val pred = IntArray(Common.PREDICTS.size)
        val prob =
            FloatArray(Common.PREDICTS.size)
        Log.e("predict", predict.size.toString() + "")
        for (i in Common.PREDICTS.indices) {
            Log.e(
                "predict", "index: " + findIndex(
                    outputBuffer.floatArray,
                    tmp!![i]
                ) + " prob:" + predict[i] + ""
            )
            prob[i] = tmp[i]
            pred[i] = findIndex(outputBuffer.floatArray, tmp[i])
        }


        Log.e("PredictSuccess", "acc=" + acc(pred, prob))
        if (acc(pred, prob) > 0.5 && !success) {
            success = true
            val labels = Common.toString(Common.PREDICTS)
            Log.e("Labels", labels)
            val faceID = FaceID(Common.ID, Common.PASSWORD, labels, 1)
            DBHelper(this@UpdateFaceActivity).updateFace(faceID)
            uploadFile()
            Log.e("PredictSuccess", "Thành công")
            Toast.makeText(applicationContext, "Cập nhật khuôn mặt thành công", Toast.LENGTH_LONG)
                .show()

        }

    }

    private fun getFeatures() {
        Common.PREDICTS = ArrayList()
        for (bitmap in bitmapList) {
            val byteBuffer = Common.convertBitmapToByteBuffer(bitmap)
            inputFeature.loadBuffer(byteBuffer)
            //đây là đưa ảnh vào model dự đoán
            val outputs = model!!.process(inputFeature)
            //đầu ra các feature
            val outputBuffer = outputs.outputFeature0AsTensorBuffer
            //dự đoán: mảng 2 chiều: 1 label, 2 %
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
        testFace()
    }

    var count = 0
    private fun testFace() {
        val handler = Handler()
        val runnable: Runnable = object : Runnable {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun run() {
                // need to do tasks on the UI thread
                var bitmap = preview_camera.bitmap
                if (bitmap != null) {
                    val image = InputImage.fromBitmap(bitmap!!, 0)
                    faceDetector.process(image).addOnSuccessListener({ faces ->
                        for (face in faces) {
                            val bounds = face.boundingBox
                            val left = bounds.left.toFloat()
                            val right = bounds.right.toFloat()
                            val top = bounds.top.toFloat()
                            val bottom = bounds.bottom.toFloat()

                            if (left < 0 || top < 0 || bottom > bitmap.getHeight() || right > bitmap.getWidth() || bitmap.getWidth() <= 0 || bitmap.getHeight() <= 0) {
                                duDoan(bitmap)
                            } else {
                                if (right - left <= 0 || bottom - top <= 0) {
                                    duDoan(bitmap)
                                } else {
                                    var faceCrop = Bitmap.createBitmap(
                                        bitmap,
                                        left.toInt(),
                                        top.toInt(),
                                        (right - left).toInt(),
                                        (bottom - top).toInt()
                                    )
                                    duDoan(faceCrop)
                                }
                            }
                            break
                        }
                    })
                }
                count++
                handler.postDelayed(this, 1000)
                if (count == 10 && !success) {
                    Log.e("Predict", "Thất bại")
                    Toast.makeText(
                        applicationContext,
                        "Cập nhật khuôn mặt thất bại",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
            }
        }
        handler.post(runnable)
    }

    private fun setupFaceDetector() {
        inputFeature = TensorBuffer.createFixedSize(intArrayOf(1, 160, 160, 3), DataType.FLOAT32)
        highAccuractiyOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()
        faceDetector = FaceDetection.getClient(highAccuractiyOpts)
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


    //check quyền camera
    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
            }
            else -> {
                // You can directly ask for the permission.
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 123)
            }
        }
    }

    //show preview cho camera x
    private fun bindPreview(@NonNull cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder()
            .build()
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
            .build()
        preview.setSurfaceProvider(preview_camera.surfaceProvider)
        val camera =
            cameraProvider.bindToLifecycle((this as LifecycleOwner), cameraSelector, preview)
    }
}