package com.tondz.thehocsinhdientu.Activities

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.tondz.thehocsinhdientu.Api.ApiService
import com.tondz.thehocsinhdientu.R
import com.tondz.thehocsinhdientu.SQLiteDatabase.DBHelper
import com.tondz.thehocsinhdientu.Utils.Common
import com.tondz.thehocsinhdientu.ml.Facenet
import kotlinx.android.synthetic.main.activity_face_detect.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class FaceDetectActivity : AppCompatActivity() {

    lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    lateinit var inputFeature: TensorBuffer
    lateinit var highAccuractiyOpts: FaceDetectorOptions
    lateinit var faceDetector: FaceDetector
    private val REQUEST_CODE = 123
    private val DELAY = 500
    var count = 0
    var flag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_detect)
        Common.setStatusBarColor(this)
        Common.PREDICTS = Common.toList(DBHelper(this@FaceDetectActivity).faceID.labels)
        checkPermission()
        cameraProviderFuture = ProcessCameraProvider.getInstance(applicationContext)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(applicationContext))
        setupFaceDetector()
        instanceModel()
        detectRealTimes()
        btn_back.setOnClickListener({
            finish()
        })
    }

    private fun attendance() {
        ApiService.api.attendance(Common.TOKEN)!!.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if (response.code() == 201) {
                    Log.e("ATTENDANCE", "Thành công")
                    Toast.makeText(
                        applicationContext,
                        "Điểm danh thành công",
                        Toast.LENGTH_LONG
                    ).show()

                    finish()
                } else if (response.code() == 405) {
                    Toast.makeText(
                        applicationContext,
                        "Đã điểm danh ngày hôm nay rồi",
                        Toast.LENGTH_LONG
                    ).show();

                    Log.e("ATTENDANCE", "Đã điểm danh ngày hôm nay")
                    finish()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Điểm danh lỗi, kiếm tra kết nối",
                        Toast.LENGTH_LONG
                    ).show();

                    Log.e("ATTENDANCE", "Lỗi" + response.message())
                    finish()
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {

                Log.e("ATTENDANCE", "Lỗi" + t.message)
                Toast.makeText(
                    applicationContext,
                    "Điểm danh lỗi, kiếm tra kết nối",
                    Toast.LENGTH_LONG
                ).show();
            }

        })
    }

    private fun detectRealTimes() {
        val handler = Handler()
        val runnable: Runnable = object : Runnable {
            override fun run() {
                // need to do tasks on the UI thread
                var bitmap = preview_camera.bitmap
                if (bitmap != null) {
                    count++;
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
                handler.postDelayed(this, DELAY.toLong())

            }
        }
        handler.post(runnable)
    }

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
        Log.e("predict", "acc=" + acc(pred, prob))
        if (acc(pred, prob) > 0.5 && !flag) {
            flag = true
            if (Common.checkLocation()) {
                attendance()

            } else {
                Toast.makeText(
                    applicationContext,
                    "Điểm danh không thành công, bạn không ở trường",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }

        }
        if (count > 5 && !flag) {
            Toast.makeText(
                applicationContext,
                "Khuôn mặt không khớp, kiểm tra lại",
                Toast.LENGTH_LONG
            ).show();
            finish()
        }

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
                requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CODE)
            }
        }
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