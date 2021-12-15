package com.mydemo.toolslist;

import android.app.DownloadManager;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

import com.mydemo.toolslist.executor.NamedThreadFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public String TAG = "TAGTAG";
    public BaseActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        activity = this;
    }

    public void openView(Class<?> classes) {
        Intent intent = new Intent(this, classes);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    private Exception exception;

    public void loge(String info){
        Log.e(TAG, "运行日志: " + info);
    }

    /**
     * TODO：文件下载
     *
     * @param downurl：下载链接
     * @param path：：下载文件存放地点
     * @param filename：下载文件命名
     * @param requestType：请求方式--GET/POST
     * @param requestProperty：请求参数
     */
    private void downloadFile(String downurl, String path, String filename,
                              String requestType, Map<String, String> requestProperty) {
        int mTimeout = 60 * 1000;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            //HttpsURLConnection.setDefaultSSLSocketFactory(SSLSocketUtils.createSSLSocketFactory());
            //HttpsURLConnection.setDefaultHostnameVerifier(SSLSocketUtils.createTrustAllHostnameVerifier());
            HttpURLConnection connect = (HttpURLConnection) new URL(downurl).openConnection();
            connect.setRequestMethod(requestType);
            connect.setRequestProperty("Accept-Encoding", "identity");

            connect.setReadTimeout(mTimeout);
            connect.setConnectTimeout(mTimeout);

            if (requestProperty != null) {
                for (Map.Entry<String, String> entry : requestProperty.entrySet()) {
                    connect.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            connect.connect();
            int responseCode = connect.getResponseCode();
            Log.d("DefaultDownloadProxy", " Content-Type: %s" + connect.getContentType());
            if (responseCode == HttpURLConnection.HTTP_OK) {
                is = connect.getInputStream();
                long length = connect.getContentLength();//获取下载文件的大小
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    length = (int) connect.getContentLengthLong();
                }
                int progress = 0;
                byte[] buffer = new byte[2048];// 4096
                int len;
                File file = new File(path, filename);
                boolean isExist = file.exists();
                if (isExist) {
                    return;
                }
                Log.e("DefaultDownloadProxy", "file 是否存在： " + isExist);
                fos = new FileOutputStream(file);
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    progress += len;
                    //更新进度
                    publishProgress(progress, length);
                }

                fos.flush();
                connect.disconnect();
                //TODO：下载完毕，这里处理下载完成之后的逻辑
            } else {//连接失败
                throw new ConnectException(String.format("responseCode = %d", responseCode));
            }
        } catch (Exception e) {
            this.exception = e;
            Log.d("DefaultDownloadProxy", e.toString());
        } finally {
            try {
                if (null != fos) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (null != is) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //dirType：Environment.DIRECTORY_DOWNLOADS
    //subPath：myDownLoad

    /**
     * DownloadManager下载文件：
     * @param context：上下文
     * @param url：下载链接
     * @param name：文件名字
     * @param dirType：存储路径
     * @param subPath：存储文件夹
     */
    public void downloadManagerInfo(Context context, String url, String name, String dirType, String subPath) {
        DownloadManager mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        //创建下载的URI
        //        String uriImg = Uri.parse("http://image.hnol.net/c/2020-11/22/15/202011221531337672-1559530.jpg")
        Uri uri = Uri.parse(url);
        //封装一个request对象
        DownloadManager.Request mRequest = new DownloadManager.Request(uri);
        //设置文件下载时的网络环境
        mRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //用于设置下载时时候在状态栏显示通知信息
        mRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        ////设置通知栏标题
        mRequest.setTitle(name + "下载中......");
        //设置Notification的message信息
        mRequest.setDescription("App下载更新中，请稍侯......");
        //用于设置漫游状态下是否可以下载
        mRequest.setAllowedOverRoaming(false);
        //设置文件存放目录
        mRequest.setDestinationInExternalFilesDir(context, dirType, subPath);

        Long id = mDownloadManager.enqueue(mRequest);
        Log.e("开始下载", "下载的id是：" + id);
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(id);

        Cursor cusor = mDownloadManager.query(query);
        if (null == cusor) {
            return;
        }
        while (cusor.moveToNext()) {
            String bytesDowload = cusor.getString(cusor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            String descrition = cusor.getString(cusor.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION));
            String idCusor = cusor.getString(cusor.getColumnIndex(DownloadManager.COLUMN_ID));
            String localUri = cusor.getString(cusor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            String mineType = cusor.getString(cusor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE));
            String title = cusor.getString(cusor.getColumnIndex(DownloadManager.COLUMN_TITLE));
            int status = cusor.getInt(cusor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            String totalSize = cusor.getString(cusor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            String timestamp = cusor.getString(cusor.getColumnIndex(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP));
            String mediaProciderUri = cusor.getString(cusor.getColumnIndex(DownloadManager.COLUMN_MEDIAPROVIDER_URI));
            String reason = cusor.getString(cusor.getColumnIndex(DownloadManager.COLUMN_REASON));
            String columnUri = cusor.getString(cusor.getColumnIndex(DownloadManager.COLUMN_URI));

            String msg = "DownloadManager--Cursor参数：\n";
            Log.e(TAG, msg + "bytesDowload：" + bytesDowload + "descrition：" + descrition
                    + "idCusor：" + idCusor + "localUri：" + localUri + "mineType：" + mineType + "title：" + title
                    + "status：" + status + "totalSize：" + totalSize + "timestamp：" + timestamp
                    + "mediaProciderUri：" + mediaProciderUri + "reason：" + reason + "columnUri：" + columnUri);

            switch (status){
                case DownloadManager.STATUS_FAILED:

                    break;
                case DownloadManager.STATUS_PAUSED:

                    break;
                case DownloadManager.STATUS_PENDING:

                    break;
                case DownloadManager.STATUS_RUNNING:

                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    Intent intent = new Intent("com.mydemo.toolslist.BroadcastReceiver");
                    intent.putExtra(DownloadManager.EXTRA_DOWNLOAD_ID, status);
                    intent.putExtra("longID", id);
                    sendBroadcast(intent);
                    //下载完成后，移除下载任务
                    mDownloadManager.remove(id);
                    break;
                default:

                    break;
            }
        }
        cusor.close();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //注册广播
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(mReceiver, intentFilter);
    }

    //todo：创建一个下载的广播,下载完成之后，发出通知

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Long downloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            int longID = intent.getIntExtra("longID", -1);
            if (downloadID == longID) {
                //todo：下载完成后的逻辑

                Log.e("结束下载", "下载完成" + downloadID);
                Toast.makeText(context, "文件下载完成", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void publishProgress(int progress, long length) {
        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
        executor.setCorePoolSize(6);
        executor.setMaximumPoolSize(6);
        executor.setKeepAliveTime(60, TimeUnit.SECONDS);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.setThreadFactory(new NamedThreadFactory("SynchronousQueue直接切换队列"));
        executor.submit(new Runnable() {
            @Override
            public void run() {
                Log.e("TAGTAG", "run: \n核心线程数：" + executor.getCorePoolSize() +
                        "\n最大线程数：" + executor.getMaximumPoolSize() +
                        "\n空闲线程保活时间：" + executor.getKeepAliveTime(TimeUnit.SECONDS) +
                        "\n线程工厂名字：" + executor.getThreadFactory().getClass().getSimpleName());
            }
        });
    }
}
