package com.tondz.thehocsinhdientu.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tondz.thehocsinhdientu.Models.Diem;
import com.tondz.thehocsinhdientu.Models.HocSinh;
import com.tondz.thehocsinhdientu.Models.News;
import com.tondz.thehocsinhdientu.Models.SchoolLocation;
import com.tondz.thehocsinhdientu.Models.ThongBao;
import com.tondz.thehocsinhdientu.R;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class Common {
        public static final String BASE_URL = "https://utehy-student-management.herokuapp.com/";
//    public static final String BASE_URL = "http://192.168.1.107:8000/";
    public static News NEWS;
    public static ThongBao THONG_BAO;
    public static List<Integer> PREDICTS;
    public static HocSinh HOC_SINH;
    public static String TOKEN;
    public static String ID, PASSWORD;
    public static SchoolLocation lStudent;
    public static Diem DIEM;

    public static boolean checkLocation() {
        //vị trí trường học
        SchoolLocation location = new SchoolLocation(20.9327999051317, 106.00866320293868);
        double r2d = 180.0D / 3.141592653589793D;
        double d2r = 3.141592653589793D / 180.0D;
        double d2km = 111189.57696D * r2d;
        double x = lStudent.getLat() * d2r;
        double y = location.getLat() * d2r;
        double distance = Math.acos(Math.sin(x) * Math.sin(y) + Math.cos(x) * Math.cos(y) * Math.cos(d2r * (lStudent.getLng() - location.getLng()))) * d2km;
        Log.e("LOCATION", distance + "m");
        if (distance <= 300) return true;
        return false;
    }


    public static List<Integer> toList(String labels) {
        List<Integer> list = new ArrayList<>();
        String[] arr = labels.split(";");
        for (int i = 0; i < arr.length - 1; i++
        ) {
            list.add(Integer.parseInt(arr[i]));
        }
        return list;
    }

    public static String toString(List<Integer> list) {
        String str = "";
        for (Integer number : list
        ) {
            str += number + ";";
        }
        return str;
    }

    public static void notiDialog(String titile, String content, Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.noti_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvTitile = dialog.findViewById(R.id.tv_titile);
        TextView tvContent = dialog.findViewById(R.id.tv_content);
        tvTitile.setText(titile);
        tvContent.setText(content);
        dialog.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static boolean checkEmpty(String content) {
        if (content.trim().equals("")) return false;
        return true;
    }

    public static void setStatusBarColor(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.primary, activity.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.primary));
        }
    }

    public static ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        //convert bitmap to bytebuffer
        float IMAGE_MEAN = 127.5f;
        float IMAGE_STD = 127.5f;
        //reize 160*160
        bitmap = Bitmap.createScaledBitmap(bitmap, 160, 160, false);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 160 * 160 * 3);
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues = new int[160 * 160];
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int pixel = 0;
        for (int i = 0; i < 160; i++) {
            for (int j = 0; j < 160; j++) {
                int input = intValues[pixel++];

                byteBuffer.putFloat((((input >> 16 & 0xFF) - IMAGE_MEAN) / IMAGE_STD));
                byteBuffer.putFloat((((input >> 8 & 0xFF) - IMAGE_MEAN) / IMAGE_STD));
                byteBuffer.putFloat((((input & 0xFF) - IMAGE_MEAN) / IMAGE_STD));
            }
        }
        return byteBuffer;
    }

    public static File savebitmap(Context context, Bitmap bitmap, String fileName) throws IOException {
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, fileName, null);
        Uri uri = Uri.parse(path);
        String realFilePath = RealPathUtils.getRealPath(context, uri);
        File file = new File(realFilePath);
        return file;
    }
}
