package com.tondz.thehocsinhdientu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tondz.thehocsinhdientu.Activities.LoginActivity
import com.tondz.thehocsinhdientu.Service.NotificationService
import com.tondz.thehocsinhdientu.Service.SocketHandler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, NotificationService::class.java)
//        startService(intent)
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        SocketHandler.setSocket()
        val mSocket = SocketHandler.getSocket()
        mSocket.connect()
        mSocket.on("test"){args->
            run {
                kotlin.run {
                    val str = args[0].toString()
                    runOnUiThread {
                        Log.e("NOTI", str)
                    }
                }
            }
        }
    }
}