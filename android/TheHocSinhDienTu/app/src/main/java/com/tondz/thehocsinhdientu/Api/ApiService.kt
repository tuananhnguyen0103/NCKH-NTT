package com.tondz.thehocsinhdientu.Api

import com.google.gson.GsonBuilder
import com.tondz.thehocsinhdientu.Api.ApiService
import com.tondz.thehocsinhdientu.Models.Diem
import com.tondz.thehocsinhdientu.Models.HocSinh
import com.tondz.thehocsinhdientu.Models.LoginModel
import com.tondz.thehocsinhdientu.Models.ThoiKhoaBieu
import com.tondz.thehocsinhdientu.Utils.Common
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


interface ApiService {
    companion object {
        val gson = GsonBuilder().setDateFormat("dd/MM/yyyy").create()
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        val api = Retrofit.Builder()
            .baseUrl(Common.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build().create(ApiService::class.java)
    }

    @POST("api/student/login")
    fun Login(@Body map: HashMap<String, String>): Call<LoginModel?>?

    @POST("api/student/attendance")
    fun attendance(@Query("token") token: String): Call<Void?>?


    @GET("api/student/getByid/{id}")
    fun getStudentById(@Path("id") id: Int): Call<HocSinh?>?


    @POST("api/student/uploadFaceIdMobile/{token}")
    @Multipart
    fun updateFace(
        @Path("token") token: String,
        @Part file: Array<MultipartBody.Part?>?
    ): Call<Void?>?

    @Multipart
    @POST("api/student/uploadAvatar/{token}")
    fun updateAvatar(
        @Path("token") token: String,
        @Part file: MultipartBody.Part?
    ): Call<Void?>?

    @GET("api/student/getTimeTable/{token}")
    fun getTimeTable(@Path("token") token: String): Call<ThoiKhoaBieu?>?

    @GET("api/student/getAllTimeTable/{token}")
    fun getAllTimeTable(@Path("token") token: String): Call<List<ThoiKhoaBieu>>

    @GET("api/student/GetAllScoreStudent/{token}")
    fun getAllScore(@Path("token") token: String): Call<List<Diem>>

    @POST("api/student/changePassword")
    fun changePassword(@Body map: HashMap<String, String>): Call<Void?>?

    @POST("api/student/InsertDayOffByStudent")
    fun insertDateOff(@Body map: HashMap<String,String>):Call<Void?>?
}