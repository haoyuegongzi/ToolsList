package com.mydemo.toolslist.bitmap;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mydemo.toolslist.BaseActivity;
import com.mydemo.toolslist.R;
import com.mydemo.toolslist.kkk.CustomerService;
import com.mydemo.toolslist.log.Logs;
import com.mydemo.toolslist.tools.DirectoryUtils;
import com.mydemo.toolslist.tools.EmptyTools;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitMapActivity extends BaseActivity {
    private String DOWNLOAD_FILE_JPG_URL = "http://img4.imgtn.bdimg.com/it/u=68625945,479175229&fm=26&gp=0.jpg";
    private ImageView ivImageStream, ivImageDecodeFile, ivImageSource, ivImageClass, ivImageByte, ivImageRegionDecoder;
    private final int PERMISSION_FLAG = 0x995;
    String prefix = Environment.getExternalStorageDirectory().getAbsolutePath();
    String sdImagePath01, sdImagePath02, sdImagePath03, sdImagePath04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bit_map);
        //初始化UI
        initViewId();
        getPath();
        //权限
        requestPermission();
    }

    private void initViewId() {
        ivImageStream = findViewById(R.id.ivImageStream);
        ivImageDecodeFile = findViewById(R.id.ivImageDecodeFile);
        ivImageSource = findViewById(R.id.ivImageSource);
        ivImageClass = findViewById(R.id.ivImageClass);
        ivImageByte = findViewById(R.id.ivImageByte);
        ivImageRegionDecoder = findViewById(R.id.ivImageRegionDecoder);



    }

    private void requestPermission() {
        boolean isStoragePermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        if (isStoragePermission) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_FLAG);
        } else {
            //加载
            loadLocal();
            loadFromSource();
            loadWithByte();
        }
    }

    private void getPath() {
        /**String prefixD = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();**/
        String prefix = Environment.getExternalStorageDirectory().getAbsolutePath();
        sdImagePath01 = prefix + "/Develop/吴娴妃.jpg";
        sdImagePath02 = prefix + "/Develop/吴娴静.jpg";
        sdImagePath03 = prefix + "/Develop/吴娴娴.jpg";
        sdImagePath04 = prefix + "/Develop/吴娴雅.jpg";
    }

    private void loadLocal() {
        try {
            //通过流Stream加载。
            FileInputStream in = new FileInputStream(sdImagePath01);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            ivImageStream.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 通过BitmapFactory.decodeFile加载 //decodeFileDescriptor
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            options.outConfig = Config.RGB_565;
        }

        Bitmap bm = BitmapFactory.decodeFile(sdImagePath02, options);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.WEBP, 100, byteArrayOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ivImageDecodeFile.setImageBitmap(bm);
    }

    //通过R.***的形式访问
    private void loadFromSource() {
        // 间接调用 BitmapFactory.decodeStream
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.wuxianxian);
        ivImageSource.setImageBitmap(bitmap);
        //drawable/wuxianya
        InputStream inputStream = getClass().getResourceAsStream("/res/drawable/xiaoxiao01.jpg");
        Bitmap bitmapClass = BitmapFactory.decodeStream(inputStream);
        ivImageClass.setImageBitmap(bitmapClass);
    }

    private void loadWithByte() {
        // InputStream转换成byte[]
        try {
            FileInputStream in = new FileInputStream(sdImagePath01);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 4];
            int n = 0;
            while (-1 != (n = in.read(buffer))) {
                output.write(buffer, 0, n);
            }
            byte[] myByte = output.toByteArray();

            Bitmap bm = BitmapFactory.decodeByteArray(myByte, 0, myByte.length);
            ivImageByte.setImageBitmap(bm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
//public static Bitmap createBitmap(Bitmap source, int x, int y, int width, int height, Matrix m, boolean filter)
//public static Bitmap createBitmap(DisplayMetrics display, @ColorInt int[] colors, int offset, int stride,int width, int height, Config config)
//public static Bitmap createScaledBitmap(Bitmap src, int dstWidth, int dstHeight,boolean filter)

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(prefix + "/Develop/吴娴娴.jpg", options);
        ivImageRegionDecoder.setImageBitmap(bitmap);

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != PERMISSION_FLAG) {
            return;
        }
        if (null == grantResults || grantResults.length == 0) {
            return;
        }
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadLocal();
            loadFromSource();
            loadWithByte();
        }
    }
}
