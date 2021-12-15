package com.mydemo.toolslist.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 作者：Created by chen1 on 2020/3/24.
 * 邮箱：chen126jie@163.com
 * TODO：
 */
public class DownLoadAsyncTask extends AsyncTask<String, Integer, String> {
    private PowerManager.WakeLock mWakeLock;
    private Context context;

    public DownLoadAsyncTask(Context context) {
        this.context = context;
    }

    /**
     * sync method which download file
     *
     * @param params
     * @return
     */
    @Override
    protected String doInBackground(String... params) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage();
            }
            int fileLength = connection.getContentLength();
            input = connection.getInputStream();
            output = new FileOutputStream(getSDCardDir());
            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                if (fileLength > 0) {
                    publishProgress((int) (total * 100 / fileLength));
                }
                Thread.sleep(100);
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
        mWakeLock.acquire();
    }

    @Override
    protected void onPostExecute(String values) {
        super.onPostExecute(values);
        mWakeLock.release();
        if (values != null) {
            Log.e("TAG", "Download error: " + values);
        } else {
            Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * set progressBar
     *
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (updateUI != null) {
            updateUI.UpdateProgressBar(values[0]);
        }
    }

    /**
     * get SD card path
     *
     * @return
     */
    public File getSDCardDir() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            // 创建一个文件夹对象，赋值为外部存储器的目录
            String dirName = Environment.getExternalStorageState() + "/MyDownload/";
            File f = new File(dirName);
            if (!f.exists()) {
                f.mkdir();
            }
            File downloadFile = new File(f, "new.jpg");
            return downloadFile;
        } else {
            Log.e("TAG", "NO SD Card!");
            return null;

        }

    }

    public UpdateUI updateUI;


    public interface UpdateUI {
        void UpdateProgressBar(Integer values);
    }

    public void setUpdateUIInterface(UpdateUI updateUI) {
        this.updateUI = updateUI;
    }
}