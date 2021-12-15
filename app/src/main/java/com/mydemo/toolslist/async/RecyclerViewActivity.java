package com.mydemo.toolslist.async;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mydemo.toolslist.BaseActivity;
import com.mydemo.toolslist.R;
import com.mydemo.toolslist.async.CustomAsyncTask;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 在使用AsyncTask时必须遵守一些规则:
 * (1) AsyncTask的实例必须在主线程（UI线程）中创建，execute方法也必须在主线程中调用
 * (2) 不要在程序中直接的调用onPreExecute(), onPostExecute(Result)，doInBackground(Params…), onProgressUpdate(Progress…)这几个方法
 * (3) 不能在doInBackground(Params… params)中更新UI
 * (5) 一个AsyncTask对象只能被执行一次，也就是execute方法只能调用一次，多次调用将会抛出异常
 */
public class RecyclerViewActivity extends BaseActivity implements CustomAsyncTask.UpdateUI {

    private String DOWNLOAD_FILE_JPG_URL = "http://img4.imgtn.bdimg.com/it/u=68625945,479175229&fm=26&gp=0.jpg";
    private ImageView ivImage;
    private Button downloadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ivImage = findViewById(R.id.ivImage);
        downloadBtn = findViewById(R.id.downloadBtn);
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncAndAsync();
            }
        });
    }

    private void syncAndAsync() {
        new CustomAsyncTask(this, "00001").execute(DOWNLOAD_FILE_JPG_URL);
        new CustomAsyncTask(this, "00002").execute(DOWNLOAD_FILE_JPG_URL);
        new CustomAsyncTask(this, "00003").execute(DOWNLOAD_FILE_JPG_URL);
        new CustomAsyncTask(this, "00004").execute(DOWNLOAD_FILE_JPG_URL);
        new CustomAsyncTask(this, "00005").execute(DOWNLOAD_FILE_JPG_URL);

        new CustomAsyncTask(this, "qqqqq").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_FILE_JPG_URL);
        new CustomAsyncTask(this, "wwwww").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_FILE_JPG_URL);
        new CustomAsyncTask(this, "eeeee").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_FILE_JPG_URL);
        new CustomAsyncTask(this, "rrrrr").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_FILE_JPG_URL);
        new CustomAsyncTask(this, "ttttt").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_FILE_JPG_URL);
    }

    private void handlerThread() {
        RecyclerView mRecyclerView = findViewById(R.id.mRecyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 0;
            }
        });

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                                       @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

            }
        });
    }

    @Override
    public void UpdateProgressBar(Bitmap bitmap) {
        ivImage.setImageBitmap(bitmap);
    }
}
