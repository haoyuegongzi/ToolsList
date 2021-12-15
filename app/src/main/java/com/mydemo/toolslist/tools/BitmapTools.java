package com.mydemo.toolslist.tools;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.exifinterface.media.ExifInterface;

/**
 * 作者：Created by chen1 on 2020/3/26.
 * 邮箱：chen126jie@163.com
 * TODO：
 */
public class BitmapTools {
    /**
     * 质量压缩方法,并不能减小加载到内存时所占用内存的空间，应该是减小的所占用磁盘的空间
     *
     * @param image
     * @param oldCompressFormat
     * @return
     */
    public static Bitmap compressbyQuality(Bitmap image, Bitmap.CompressFormat oldCompressFormat, Bitmap.CompressFormat newCompressFormat) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(oldCompressFormat, 100, baos);
        int quality = 100;
        //循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 100) {
            //重置baos即清空baos
            baos.reset();
            if (quality > 10) {
                //每次都减少20
                quality -= 20;
            } else {
                break;
            }
            //这里压缩options%，把压缩后的数据存放到baos中
            image.compress(newCompressFormat, quality, baos);
        }
        //把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //把ByteArrayInputStream数据生成图片
        Bitmap bmp = BitmapFactory.decodeStream(isBm, null, options);
        return bmp;
    }

    //采样压缩
    private Bitmap compressByCompression(Context context, int id, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //inJustDecodeBounds为true，不返回bitmap，只返回这个bitmap的尺寸
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), id, options);
        //利用返回的原图片的宽高，我们就可以计算出缩放比inSampleSize(只能是2的整数次幂),使用RGB_565减少图片大小
        options.inSampleSize = calculateSampleSize(options, reqWidth, reqHeight);
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //释放内存，共享引用（21版本后失效）
        options.inPurgeable = true;
        options.inInputShareable = true;
        //inJustDecodeBounds为false，返回bitmap
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id, options);
        return bitmap;
    }

    //采样压缩的百分比
    private int calculateSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        while ((halfWidth / inSampleSize) >= reqWidth &&
                (halfHeight / inSampleSize) >= reqHeight) {
            inSampleSize *= 2;
        }
        return inSampleSize;
    }

    //drawable的获取方式：Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap.Config mconfig = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), mconfig);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public Bitmap drawable2Bitmap(BitmapDrawable bitmapDrawable){
        Bitmap bitmap = bitmapDrawable.getBitmap();
        return bitmap;
    }

    //Bitmap转Drawable
    public static Drawable bitmapToDrawable(Resources resources, Bitmap bm) {
        Drawable drawable = new BitmapDrawable(resources, bm);
        return drawable;
    }

    //Bitmap转Bytes
    public byte[] bitmapToBytes(Bitmap bm, Bitmap.CompressFormat format) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(format, 100, baos);
        return baos.toByteArray();
    }

    //byte转Bitmap
    public Bitmap byteToBitmap(byte[] b) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        return bitmap;
    }

    //inputstream转Bitmap
    public Bitmap inputstreamToBitmap(Context context, int id) {
        InputStream is = context.getResources().openRawResource(id);
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }

    //inputstream转Byte
    public byte[] inputstreamToByte(Context context, int id) {
        //也可以通过其他方式接收一个InputStream对象
        InputStream is = context.getResources().openRawResource(id);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = null;
        byte[] b = new byte[1024 * 2];
        int len = 0;
        try {
            while ((len = is.read(b, 0, b.length)) != -1) {
                baos.write(b, 0, len);
                baos.flush();
            }
            bytes = baos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bytes;
    }

    //View转为BitMap，无Background信息
    public static Bitmap convertViewToBitMap(View view){
        // 打开图像缓存
        view.setDrawingCacheEnabled(true);
        // 必须调用measure和layout方法才能成功保存可视组件的截图到png图像文件
        // 测量View大小
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        // 发送位置和尺寸到View及其所有的子View
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        // 获得可视组件的截图
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    //View转为BitMap，如果Background信息存在，也会被采用
    public static Bitmap getBitmapFromView(View view){
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        }else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return bitmap;
    }

    /**
     *
     * @param filePath: 保存路径
     * @param b
     * @param format：保存的图片格式，png、JPEG
     * @param quality：保存的质量，100%，80%
     */
    public static void saveBitmapToLocal(String filePath, Bitmap b, Bitmap.CompressFormat format, int quality) {
        try {
            File desFile = new File(filePath);
            FileOutputStream fos = new FileOutputStream(desFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            b.compress(format, quality, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //图片缩放
    public static Bitmap bitmapScale(Bitmap bitmap, float scale) {
        Matrix matrix = new Matrix();
        // 长和宽放大缩小的比例
        matrix.postScale(scale, scale);
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    //获取图片旋转角度
    private static int getPictuRerotateDegree(String path) {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    degree = 0;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return degree;
    }

    //设置图片旋转角度
    private static Bitmap setRotateBitmapDegree(Bitmap b, float rotateDegree) {
        if (b == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(rotateDegree);
        Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
        return rotaBitmap;
    }
}
