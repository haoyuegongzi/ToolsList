package com.mydemo.toolslist.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 作者：Created by chen1 on 2020/3/24.
 * 邮箱：chen126jie@163.com
 * TODO：
 */

/**
 * AsyncTask后面的三个参数：
 * 第一个参数：对应DoInBackground方法里面的参数类型；
 * 第二个参数：对应onProgressUpdate方法里面的参数类型；
 * 第三个参数：对应doInBackground方法的返回值以及onPostExecute方法的参数
 */
public class CustomAsyncTask extends AsyncTask<String, Integer, Bitmap> {
    private String threadName;
    public CustomAsyncTask(UpdateUI mUpdateUI, String name){
        updateUI = mUpdateUI;
        threadName = name;
    }


    /**
     * onPreExecute是可以选择性覆写的方法
     * 在主线程中执行,在异步任务执行之前,该方法将会被调用一般用来在执行后台任务前对UI做一些标记和准备工作，如在界面上显示一个进度条。
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * 抽象方法，必须实现，在线程池中被调用,异步任务就在该方法里面执行
     * 将在onPreExecute方法执行后执行。其参数是一个可变类型，表示异步任务的输入参数，在该方法中还可通过publishProgress(Progress… values)
     * 来更新实时的任务进度，而publishProgress方法则会调用onProgressUpdate方法。
     * 此外doInBackground方法会将计算的返回结果传递给onPostExecute方法。
     * @param strings
     * @return
     */
    @Override
    protected Bitmap doInBackground(String... strings) {
        //TODO：一步任务的具体逻辑在这里实现
        Bitmap bitmap = null;
        try {
            URL imageurl = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * onProgressUpdate是可以选择性覆写的方法
     * 在主线程中执行
     * 当该方法在父类的publishProgress(Progress… values)方法被调用后执行，一般用于更新UI进度，如更新进度条的当前进度。
     *
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.e("TAGTAG", "onProgressUpdate下载进度： " + values );
    }

    /**
     * 可选覆写的方法，在主线程中执行
     * 在doInBackground执行完成后，被UI线程调用，
     * doInBackground方法的返回值将作为此方法的参数传递到UI线程中，并执行一些UI更新的相关操作。
     *
     * @param bitmap
     */
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (null != updateUI){
            updateUI.UpdateProgressBar(bitmap);
        }
        Log.e("TAGTAG", "onPostExecute：" + threadName + "--" + Thread.currentThread().getName() +
                                    "--" + Thread.currentThread().getId());
    }

    /**
     * onCancelled是可以选择性覆写的方法
     * 在主线程中执行
     * 当异步任务被取消时,该方法将被调用,要注意的是这个时候onPostExecute将不会被执行
     */
    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    public UpdateUI updateUI;

    public interface UpdateUI {
        void UpdateProgressBar(Bitmap bitmap);
    }
}
