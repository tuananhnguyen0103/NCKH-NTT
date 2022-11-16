package com.tondz.thehocsinhdientu.Service

import android.util.Log
import com.tondz.thehocsinhdientu.Utils.Common
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {
    lateinit var mSocket: Socket

    @Synchronized
    fun setSocket() {
        try {
            mSocket = IO.socket(Common.BASE_URL)
        } catch (e: URISyntaxException) {
            Log.e("SOCKET", e.reason)
        }
    }
    @Synchronized
    fun getSocket() : Socket{
        return mSocket
    }
}