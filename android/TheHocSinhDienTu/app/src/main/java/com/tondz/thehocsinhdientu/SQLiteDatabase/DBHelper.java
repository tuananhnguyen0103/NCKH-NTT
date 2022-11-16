package com.tondz.thehocsinhdientu.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.tondz.thehocsinhdientu.Models.FaceID;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "database.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table FaceID(" +
                "idHocSinh varchar(20) primary key," +
                "password nvarchar(20)," +
                "labels ntext," +
                "status bit)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public FaceID getFaceID() {
      FaceID faceID = null;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from FaceID", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            faceID = new FaceID(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
            cursor.moveToNext();
        }
        return faceID;
    }

    public List<FaceID> getAll() {
        List<FaceID> faceIDList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from FaceID", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            faceIDList.add(new FaceID(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)));
            cursor.moveToNext();
        }
        return faceIDList;
    }

    public void updateFace(FaceID faceID) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idHocSinh", faceID.getIdHocSinh());
        values.put("password", faceID.getPassword());
        values.put("labels", faceID.getLabels());
        values.put("status", faceID.getStatus());
        if (getFaceID() != null) {
            int rs = database.update("FaceID", values, "idHocSinh = ?", new String[]{faceID.getIdHocSinh()});
            Log.e("Update", rs + " update");
        } else {
            database.delete("FaceID", null, null);
            long rs = database.insert("FaceID", null, values);
            Log.e("Update", rs + " insert");
        }
    }


}
