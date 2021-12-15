package com.mydemo.toolslist.async;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * 作者：Created by chen1 on 2020/4/14.
 * 邮箱：chen126jie@163.com
 * TODO：
 */
public class DownLoadFileAsyncTask extends AsyncTask<String, Integer, File> {
    private PowerManager.WakeLock mWakeLock;
    private Context context;

    public DownLoadFileAsyncTask(Context context) {
        this.context = context;
    }

    /**
     * sync method which download file
     *
     * @param params
     * @return
     */
    @Override
    protected File doInBackground(String... params) {
        File file = downloadFile(params[0], new HashMap<String, String>());
//        InputStream input = null;
//        OutputStream output = null;
//        HttpURLConnection connection = null;
//        try {
//            URL url = new URL(params[0]);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.connect();
//            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
//                return "Server returned HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage();
//            }
//            int fileLength = connection.getContentLength();
//            input = connection.getInputStream();
//            output = new FileOutputStream(getSDCardDir());
//            byte data[] = new byte[4096];
//            long total = 0;
//            int count;
//            while ((count = input.read(data)) != -1) {
//                if (isCancelled()) {
//                    input.close();
//                    return null;
//                }
//                total += count;
//                if (fileLength > 0) {
//                    publishProgress((int) (total * 100 / fileLength));
//                }
//                Thread.sleep(100);
//                output.write(data, 0, count);
//            }
//        } catch (Exception e) {
//            return e.toString();
//        } finally {
//            try {
//                if (output != null) {
//                    output.close();
//                }
//                if (input != null) {
//                    input.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if (connection != null) {
//                connection.disconnect();
//            }
//        }
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
    protected void onPostExecute(File values) {
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

    private File downloadFile(String url, Map<String, String> map){
        String path = "", filename = "";
        int mTimeout = 30 * 1000;

        HttpURLConnection connect = null;
        FileOutputStream fos = null;
        InputStream is = null;
        try {
//            HttpsURLConnection.setDefaultSSLSocketFactory(SSLSocketUtils.createSSLSocketFactory());
//            HttpsURLConnection.setDefaultHostnameVerifier(SSLSocketUtils.createTrustAllHostnameVerifier());
            connect = (HttpURLConnection) new URL(url).openConnection();
            connect.setRequestMethod("GET");
            connect.setRequestProperty("Accept-Encoding", "identity");

            connect.setReadTimeout(mTimeout);
            connect.setConnectTimeout(mTimeout);

            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    connect.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            connect.connect();
            int responseCode = connect.getResponseCode();
            Log.d("DefaultDownloadProxy", " Content-Type: %s" + connect.getContentType());
            if (responseCode == HttpURLConnection.HTTP_OK) {
                is = connect.getInputStream();
                int length = connect.getContentLength();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    length = (int) connect.getContentLengthLong();
                }
                int progress = 0;
                byte[] buffer = new byte[2048];// 4096
                int len;
                File file = new File(path, filename);
                boolean isExist = file.exists();
                Log.e("DefaultDownloadProxy", "file 是否存在： " + isExist);
                fos = new FileOutputStream(file);
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    progress += len;
                    //更新进度
                    publishProgress(progress, length);
                }

                fos.flush();
                fos.close();
                is.close();
                connect.disconnect();
                return file;
            } else {//连接失败
                throw new ConnectException(String.format("responseCode = %d", responseCode));
            }
        } catch (Exception e) {
            Log.d("DefaultDownloadProxy", e.toString());
        }finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connect != null) {
                connect.disconnect();
            }
        }
        return null;
    }

    public DownLoadAsyncTask.UpdateUI updateUI;


    public interface UpdateUI {
        void UpdateProgressBar(Integer values);
    }

    public void setUpdateUIInterface(DownLoadAsyncTask.UpdateUI updateUI) {
        this.updateUI = updateUI;
    }
}
