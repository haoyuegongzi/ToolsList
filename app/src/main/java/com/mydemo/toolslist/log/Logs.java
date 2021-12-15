package com.mydemo.toolslist.log;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.ResponseBody;

/**
 * 作者：Created by chen1 on 2020/3/16.
 * 邮箱：chen126jie@163.com
 * TODO：
 */
public class Logs {
    public static String many = "中国现代国际关系研究院学者李峥在接受参考消息网采访时称，当前，日韩两国的分歧已导致了原有产业链的不稳定，一些日韩公司会考虑是否更多地让中国成为产业链中的一环，甚至将部分产业链转移至中国。一方面，在日本长期掌控的半导体尖端材料领域，一些日本企业会来华进行投产；另一方面，在半导体零部件制造领域，一些来自韩国的产能将转移至中国。";
    public static String little = "中国现代国际关系研究院学者李峥在接受参考消息网采访时称，当前，日韩两国的分歧已导致了原有产业链的不稳定";

    static String TAG = "TAGTAG";
    public static void log(String s){
        Log.e(TAG, "log----->>>" + s);
    }
}


