package com.mydemo.toolslist.executor;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.mydemo.toolslist.BaseActivity;
import com.mydemo.toolslist.R;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

public class ExecutorActivity extends BaseActivity {

    TextView textView;

    long time = 10000000L;
    int day = 0;
    int h = 0;
    int m = 0;
    int s = 0;

    ScheduledExecutorService executorService;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String message = (String) msg.obj;
            textView.setText(message + "");
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executor);
        textView = new TextView(this);

        executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                if (time > 0) {
                    day = (int) (time / (24 * 60 * 60));
                    h = (int) ((time - day * 24 * 60 * 60) / (60 * 60));
                    m = (int) ((time - day * 24 * 60 * 60 - h * 60 * 60) / 60);
                    s = (int) (time - day * 24 * 60 * 60 - h * 60 * 60 - m * 60);
                    time--;
                }
                Message message = new Message();
                message.what = 1;
                message.obj = "还剩 " + day + " 天 " + h + " 小时" + m + " 分" + s + " 秒";
                handler.sendMessage(message);
            }
        }, 1, 1, TimeUnit.SECONDS);

        try {
            scheduledTimer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scheduledTimerExecutor();
    }

    ScheduledExecutorService executorService00;
    ScheduledExecutorService executorService01;
    ScheduledExecutorService executorService02;
    ScheduledExecutorService executorService03;
    ScheduledExecutorService executorService04;
    ScheduledExecutorService executorService05;
    ScheduledExecutorService executorService06;
    ThreadPoolExecutor threadPoolExecutor;
    ScheduledThreadPoolExecutor executor;

    private void scheduledTimer() throws InterruptedException {
        TimeUnit unit;
        BlockingQueue workQueue;
        threadPoolExecutor = new ThreadPoolExecutor(7, 7, 60,
                TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setName("斩武道.护世");
                thread.setPriority(Thread.MAX_PRIORITY);
                return thread;
            }
        }, new ThreadPoolExecutor.CallerRunsPolicy());


        executorService00 = new ScheduledThreadPoolExecutor(2);
        executorService00.schedule(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Log.i(TAG, "ScheduledExecutorService00 \n ScheduledThreadPoolExecutor的schedule方法接收一个Callable对象作为参数");
                return null;
            }
        }, 1, TimeUnit.SECONDS);

        executorService01 = new ScheduledThreadPoolExecutor(2);
        // schedule方法调度的任务在delay时长的延迟后只执行一次。
        executorService01.schedule(runnable01, 3, TimeUnit.SECONDS);

        executorService02 = new ScheduledThreadPoolExecutor(2);
        // scheduleAtFixedRate：该方法在initialDelay时长后第一次执行任务，以后每隔period时长，再次执行任务。
        // 注意，period是从任务开始执行算起的。开始执行任务后，定时器每隔period时长检查该任务是否完成，
        // 如果完成则再次启动任务，否则等该任务结束后才再次启动任务。
        executorService02.scheduleAtFixedRate(runnable02, 2, 2, TimeUnit.SECONDS);

        executorService03 = new ScheduledThreadPoolExecutor(2);
        // 该方法在initialDelay时长后第一次执行任务，以后每当任务执行完成后，等待delay时长，再次执行任务。
        executorService03.scheduleWithFixedDelay(runnable03, 1, 1, TimeUnit.SECONDS);

        executorService04 = new ScheduledThreadPoolExecutor(2);
        executorService04.execute(runnable04);

        executorService05 = new ScheduledThreadPoolExecutor(3);
        executorService05.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Log.i(TAG, "ScheduledExecutorService05 \n ScheduledThreadPoolExecutor实现了ScheduledExecutorService接口，" +
                        "继承于ThreadPoolExecutor");
                return new String("ScheduledThreadPoolExecutor实现了ScheduledExecutorService接口");
            }
        });

        executorService06 = new ScheduledThreadPoolExecutor(3);
        executorService06.submit(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "ScheduledExecutorrunnable06 \n ScheduledThreadPoolExecutor实现了ScheduledExecutorService接口，" +
                        "继承于ThreadPoolExecutor");
            }
        });

        executorService03.shutdown();
        Log.i(TAG, "ScheduledExecutorscheduledTimer: isShutdown===" + executorService03.isShutdown());
        Log.i(TAG, "ScheduledExecutorTimer: isTerminated===" + executorService03.isTerminated());

        boolean awaitTermination = executorService03.awaitTermination(5, TimeUnit.SECONDS);
        Log.i(TAG, "ScheduledExecutorscheduledTimer: awaitTermination===" + awaitTermination);
        if (awaitTermination) {
            Log.i(TAG, "ScheduledExecutorscheduledTimer: 线程池没有关闭===");
        }

        executor = new ScheduledThreadPoolExecutor(5);
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

    private void scheduledTimerExecutor() {
        executor = new ScheduledThreadPoolExecutor(5);
        executor.setCorePoolSize(6);
        executor.setMaximumPoolSize(6);
        executor.setKeepAliveTime(60, TimeUnit.SECONDS);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.setThreadFactory(new NamedThreadFactory("SynchronousQueue直接切换队列"));
        executor.submit(new Runnable() {
            @Override
            public void run() {
                Log.e("TAGTAG", "核心线程数：" + executor.getCorePoolSize() +
                        "\n最大线程数：" + executor.getMaximumPoolSize() +
                        "\n空闲线程保活时间：" + executor.getKeepAliveTime(TimeUnit.SECONDS) +
                        "\n线程工厂名字：" + executor.getThreadFactory().getClass().getSimpleName());
            }
        });

        ExecutorService executor00 = Executors.newSingleThreadExecutor();
        ExecutorService executor01 = Executors.newFixedThreadPool(6);
        ExecutorService executor02 = Executors.newCachedThreadPool();
        ExecutorService executor03 = Executors.newScheduledThreadPool(6);
        ExecutorService executor04 = Executors.newSingleThreadScheduledExecutor();

        ScheduledExecutorService executorService05 = new ScheduledThreadPoolExecutor(3);

        // SynchronousQueue直接切换队列：该队列传递任务到线程而不持有它们。在这一点上，试图向该队列压入一个任务，
        // 如果没有可用的线程立刻运行任务，那么就会入列失败，所以一个新的线程就会被创建。当处理那些内部依赖的任务集合时，
        // 这个选择可以避免锁住。直接接传递通常需要无边界的最大线程数来避免新提交任务被拒绝处理。当任务以平均快于被处理的速度提交到线程池时，
        // 它依次地确认无边界线程增长的可能性
        ThreadPoolExecutor poolExecutorSyn = new ThreadPoolExecutor(6, 6, 60,
                TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new NamedThreadFactory("SynchronousQueue直接切换队列"));
        poolExecutorSyn.execute(runnable04);

        // LinkedBlockingQueue使用无界队列：使用这个队列的话，没有预先定义容量的无界队列，最大线程数是为corePoolSize，
        // 在核心线程都繁忙的时候会使新提交的任务在队列中等待被执行，所以将不会创建更多的线程，这时候，maximunPoolSize最大线程数的值将不起作用。
        // 当每个任务之间是相互独立的时比较适合该队列，任务之间不能互相影响执行。
        ThreadPoolExecutor poolExecutorLinked = new ThreadPoolExecutor(6, 6, 60,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new CustomThreadFactory("LinkedBlockingQueue无界队列"));
        poolExecutorLinked.execute(new Runnable() {
            @Override
            public void run() {
                Log.i("TAGTAG", "自定义的ThreadFactory的线程优先级信息：" + Thread.currentThread().getName()
                        + "\n" + Thread.currentThread().getPriority());
            }
        });

        // ArrayBlockingQueue使用有界队列：使用这个队列，线程池中的最大线程数量就是maximunPoolSize，能够降低资源消耗，
        // 但是却使得线程之间调度变得更加困难，因为队列容量和线程池都规定完了。
        ThreadPoolExecutor poolExecutorArray = new ThreadPoolExecutor(6, 6, 60,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100), new ThreadFactory() {
            @Override
            public Thread newThread(@NonNull Runnable r) {
                Thread thread = new Thread(r, "ArrayBlockingQueue有界队列");
                Log.i(TAG, "ThreadFactory默认定义的线程优先级：" + thread.getPriority());
                thread.setPriority(8);
                Log.i(TAG, "ThreadFactory默认定义的线程优先级：" + thread.getPriority());
                return thread;
            }
        }, new RejectedStrategy());
        poolExecutorArray.execute(new Runnable() {
            @Override
            public void run() {
                Log.i("TAGTAG", "ThreadFactory默认定义的线程优先级：" + Thread.currentThread().getName()
                        + "\n" + Thread.currentThread().getPriority());
            }
        });


    }

    Runnable runnable01 = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "ScheduledExecutorrunnable01 \n ScheduledThreadPoolExecutor的schedule方法接收一个Runnable对象作为参数");
        }
    };

    Runnable runnable02 = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "ScheduledExecutorrunnable02 \n ScheduledThreadPoolExecutor的schedule方法接收一个Runnable对象作为参数");
        }
    };

    Runnable runnable03 = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "ScheduledExecutorrunnable03 \n ScheduledThreadPoolExecutor的schedule方法接收一个Runnable对象作为参数");
        }
    };

    Runnable runnable04 = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "ScheduledExecutorrunnable04 \n ScheduledThreadPoolExecutor的schedule方法接收一个Runnable对象作为参数");
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        AbstractQueuedSynchronizerInfo aqsInfo = new AbstractQueuedSynchronizerInfo();

        
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在需要的地方销毁executorService，避免内存泄漏。
        executorService01.shutdown();
        executorService02.shutdown();
        executorService03.shutdown();
        executorService04.shutdown();
        executorService.shutdown();
        executorService.shutdownNow();
    }
}