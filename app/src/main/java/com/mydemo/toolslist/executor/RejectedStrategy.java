package com.mydemo.toolslist.executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: Nixon
 * @ProjectName: ToolsList
 * @Package: com.mydemo.toolslist.executor
 * @ClassName: RejectedStrategy
 * @CreateDate: 2020/12/2 21:35
 * @Description: 本类作用描述：
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
public class RejectedStrategy implements RejectedExecutionHandler {
    Map<ThreadPoolExecutor, Runnable> runnableMap = new ConcurrentHashMap<>();

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        /* todo:创建一个缓存队列，存放超过阻塞队列范围的任务 */
        runnableMap.put(executor, r);
    }

    public void getRunnable(){
        if (runnableMap.isEmpty()){
            return;
        }
        Iterator<Map.Entry<ThreadPoolExecutor, Runnable>> iterator = runnableMap.entrySet().iterator();
        if (!iterator.hasNext()){
            return;
        }
        Map.Entry<ThreadPoolExecutor, Runnable> entry = iterator.next();
        ThreadPoolExecutor executor = entry.getKey();
        Runnable runnable = entry.getValue();
        executor.execute(runnable);

        Future future = executor.submit(runnable);

        executor.shutdown();
        executor.shutdownNow();
    }
}
