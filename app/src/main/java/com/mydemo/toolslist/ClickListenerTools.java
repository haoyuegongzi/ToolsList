package com.mydemo.toolslist;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mydemo.toolslist.annotation.AnnotationActivity;
import com.mydemo.toolslist.async.RecyclerViewActivity;
import com.mydemo.toolslist.bitmap.BitMapActivity;
import com.mydemo.toolslist.broadcast.BroadcastActivity;
import com.mydemo.toolslist.dialog.DialogActivity;
import com.mydemo.toolslist.domain.PingDomainActivity;
import com.mydemo.toolslist.executor.ExecutorActivity;
import com.mydemo.toolslist.handler.HandlerActivity;
import com.mydemo.toolslist.kkk.CustomerIntentService;
import com.mydemo.toolslist.kkk.CustomerService;
import com.mydemo.toolslist.kkk.ShowEWMPopupWindow;
import com.mydemo.toolslist.livedata.LiveDataActivity;
import com.mydemo.toolslist.log.Logs;
import com.mydemo.toolslist.motionlayout.MotionLayoutActivity;
import com.mydemo.toolslist.notification.MonitorActivity;
import com.mydemo.toolslist.sliding.SlidingActivity;
import com.mydemo.toolslist.sms.SMSMonitorActivity;
import com.mydemo.toolslist.socket.OkHttpSocketActivity;
import com.mydemo.toolslist.span.SpannableActivity;
import com.mydemo.toolslist.touch.ItemTouchHelperActivity;
import com.mydemo.toolslist.user_defined_ui.UserDefinedUiActivity;
import com.mydemo.toolslist.viewpager2.ViewPager2Activity;
import com.mydemo.toolslist.viewpager2.blank.BlankActivity;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import static com.mydemo.toolslist.log.Logs.log;

/**
 * @author: Nixon
 * @ProjectName: ToolsList
 * @Package: com.mydemo.toolslist
 * @ClassName: ClickListenerTools
 * @CreateDate: 2021/4/9 11:28
 * @Description: 本类作用描述：
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
public class ClickListenerTools {

    private MainActivity activity;
    private AppCompatTextView autotvMany, autotvLittle;

    private boolean post;
    private CustomerIntentService intentService;
    private ShowEWMPopupWindow ewmPopupWindow;

    public ClickListenerTools(MainActivity activity, AppCompatTextView autotvMany,
                              AppCompatTextView autotvLittle, CustomerIntentService intentService) {
        this.activity = activity;
        this.autotvMany = autotvMany;
        this.autotvLittle = autotvLittle;
        this.intentService = intentService;
    }

    private ServiceConnection intentServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CustomerIntentService.MyBinder myBinder = (CustomerIntentService.MyBinder) service;
            String info = myBinder.getHandleIntentInfo();
            Toast.makeText(activity, info, Toast.LENGTH_SHORT).show();
            log(info);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            log("intentServiceConnection-->>>onServiceDisconnected，name：" + name);
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            log("serviceConnection---IBinder service");
            CustomerService.MyIBinder myIBinder = (CustomerService.MyIBinder) service;
            myIBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            log("onServiceDisconnected");
        }
    };

    public void serviceListener() {
        activity.findViewById(R.id.btnIntentServiceBind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, intentService.getClass());
                intent.putExtra("name", "晓晓");
                activity.startService(intent);
                activity.bindService(intent, intentServiceConnection, activity.BIND_AUTO_CREATE);
            }
        });

        activity.findViewById(R.id.btnIntentServiceUnBind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.unbindService(intentServiceConnection);
            }
        });

        activity.findViewById(R.id.btnIntentServiceStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, intentService.getClass());
                intent.putExtra("name", "晓晓");
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startService(intent);
                activity.startService(intent);
            }
        });

        activity.findViewById(R.id.btnIntentServiceStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CustomerIntentService.class);
                activity.stopService(intent);
            }
        });

        activity.findViewById(R.id.btnGetMeasureSpec).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post = autotvMany.post(new Runnable() {
                    @Override
                    public void run() {
                        log("post======：" + post);
                        if (post) {
                            int width = autotvMany.getWidth();
                            log("autotvMany的宽度：" + width);
                        }
                    }
                });
            }
        });

        activity.findViewById(R.id.btnStartService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CustomerService.class);
                activity.startService(intent);
            }
        });

        activity.findViewById(R.id.btnBindService).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CustomerService.class);
                activity.bindService(intent, serviceConnection, activity.BIND_AUTO_CREATE);
            }
        });

        activity.findViewById(R.id.btnUnBindService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.unbindService(serviceConnection);
            }
        });

        activity.findViewById(R.id.btnStopService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CustomerService.class);
                activity.stopService(intent);
            }
        });

        activity.findViewById(R.id.btnStopService).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });

        activity.findViewById(R.id.btnPopupWindow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == ewmPopupWindow) {
                    ewmPopupWindow = new ShowEWMPopupWindow(activity);
                }
                ewmPopupWindow.showPopupWindow(autotvLittle);
            }
        });
    }

    public void rxJavaListener() {
        activity.findViewById(R.id.btnRxJavaCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaCreate();
            }
        });

        activity.findViewById(R.id.btnRxJavaMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaMap();
            }
        });

        activity.findViewById(R.id.btnRxJavaZip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaZip();
            }
        });

        activity.findViewById(R.id.btnRxJavaConcat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaConcat();
            }
        });

        activity.findViewById(R.id.btnRxJavaFlatMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaFlatMap();
            }
        });

        activity.findViewById(R.id.btnRxJavaConcatMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaConcatMap();
            }
        });

        activity.findViewById(R.id.btnRxJavaDistinct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaDistinct();
            }
        });

        activity.findViewById(R.id.btnRxJavaFilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaFilter();
            }
        });

        activity.findViewById(R.id.btnRxJavaBuffer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaBuffer();
            }
        });

        activity.findViewById(R.id.btnRxJavaTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaTimer();
            }
        });

        activity.findViewById(R.id.btnRxJavaInterval).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaInterval();
            }
        });

        activity.findViewById(R.id.btnRxJavaDoOnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaDoOnNext();
            }
        });

        activity.findViewById(R.id.btnRxJavaSkip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaSkip();
            }
        });

        activity.findViewById(R.id.btnRxJavaTake).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaTake();
            }
        });

        activity.findViewById(R.id.btnRxJavaSingleObserver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaSingleObserver();
            }
        });

        activity.findViewById(R.id.btnRxJavaDebounce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaDebounce();
            }
        });

        activity.findViewById(R.id.btnRxJavaDefer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaDefer();
            }
        });

        activity.findViewById(R.id.btnRxJavaLast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaLast();
            }
        });

        activity.findViewById(R.id.btnRxJavaMerge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaMerge();
            }
        });

        activity.findViewById(R.id.btnRxJavaReduce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaReduce();
            }
        });

        activity.findViewById(R.id.btnRxJavaScan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRxJavaScan();
            }
        });
    }

    boolean isGone = true;
    ConstraintLayout clOpenView;
    TextView tvControlNestedScrollView;

    public void openViewListener() {
        clOpenView = activity.findViewById(R.id.clOpenView);
        tvControlNestedScrollView = activity.findViewById(R.id.tvControlNestedScrollView);
        if (isGone) {
            clOpenView.setVisibility(View.GONE);
        }

        activity.findViewById(R.id.tvControlNestedScrollView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isGone = !isGone;
                if (isGone) {
                    clOpenView.setVisibility(View.GONE);
                } else {
                    clOpenView.setVisibility(View.VISIBLE);
                }
            }
        });

        activity.findViewById(R.id.btnUserDefinedUi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openView(UserDefinedUiActivity.class);
            }
        });

        activity.findViewById(R.id.btnBroadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openView(BroadcastActivity.class);
            }
        });

        activity.findViewById(R.id.autotvMany).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openView(RecyclerViewActivity.class);
            }
        });

        activity.findViewById(R.id.autotvLittle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openView(BitMapActivity.class);
            }
        });

        activity.findViewById(R.id.btnViewPager2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openView(ViewPager2Activity.class);
            }
        });

        activity.findViewById(R.id.btnViewPager2Frag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activity.openView(ViewPager2FragmentActivity.class);
            }
        });

        activity.findViewById(R.id.btnViewPager2FragBlank).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openView(BlankActivity.class);
            }
        });

        activity.findViewById(R.id.btnNotification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_p = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                activity.startActivity(intent_p);
                activity.openView(MonitorActivity.class);
                //activity.openView(NotificationActivity.class);
            }
        });

        activity.findViewById(R.id.tvDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openView(DialogActivity.class);
            }
        });

        activity.findViewById(R.id.btnSMSMonitor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openView(SMSMonitorActivity.class);
            }
        });

        activity.findViewById(R.id.btnOkHttpSocket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openView(OkHttpSocketActivity.class);
            }
        });

        activity.findViewById(R.id.btnMotionLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openView(MotionLayoutActivity.class);
            }
        });

        activity.findViewById(R.id.btnAnnotation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openView(AnnotationActivity.class);
            }
        });

        activity.findViewById(R.id.btnExecutor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openView(ExecutorActivity.class);
            }
        });
        activity.findViewById(R.id.btnLiveData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = App.app;
                // Context context = activity.getApplicationContext();
                Intent intent = new Intent(App.app, LiveDataActivity.class);
                // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        activity.findViewById(R.id.btnItemTouchHelper).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openView(ItemTouchHelperActivity.class);
            }
        });

        activity.findViewById(R.id.btnSlidingPaneLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openView(SlidingActivity.class);
            }
        });

        activity.findViewById(R.id.btnSpannable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openView(SpannableActivity.class);
            }
        });

        activity.findViewById(R.id.btnHandler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openView(HandlerActivity.class);
            }
        });

        activity.findViewById(R.id.btnPingDomain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openView(PingDomainActivity.class);
            }
        });
    }

    private Disposable disposable;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private void btnRxJavaCreate() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                        emitter.onNext(5);
                        emitter.onComplete();
                        Logs.log("observable被观察者所在线程：" + Thread.currentThread().getName());
                    }
                })
                //        .subscribeOn(Schedulers.newThread())
                //        .observeOn(Schedulers.newThread())
                //        .observeOn(AndroidSchedulers.mainThread())
                //        .observeOn(Schedulers.io())
                //        .observeOn(Schedulers.computation())


                .subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                        compositeDisposable.add(disposable);
                        Logs.log("onSubscribe观察者所在线程：" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        Logs.log("onNext观察者所在线程：" + Thread.currentThread().getName());
                        Logs.log("收到的信息------" + integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Logs.log("onError观察者所在线程：" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        Logs.log("onComplete观察者所在线程：" + Thread.currentThread().getName());
                    }
                });
        //        Observable.create(new ObservableOnSubscribe<Integer>() {
        //            @Override
        //            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
        //                emitter.onNext(5);
        //                emitter.onComplete();
        //                log("observable被观察者所在线程：" + Thread.currentThread().getName());
        //            }
        //        }).subscribe(new Consumer<Integer>() {
        //            @Override
        //            public void accept(Integer integer) throws Exception {
        //
        //            }
        //        });


        //        Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
        //            @Override
        //            public void subscribe(@NotNull ObservableEmitter emitter) {
        //                emitter.onNext(1);
        //                emitter.onNext(2);
        //                emitter.onNext(3);
        //                emitter.onNext(4);
        //                emitter.onComplete();
        //                emitter.onNext(5);
        //                log("observable被观察者所在线程：" + Thread.currentThread().getName());
        //            }
        //        });
        //
        //        Observer observer = new Observer() {
        //            @Override
        //            public void onSubscribe(@NotNull Disposable d) {
        //                disposable = d;
        //                compositeDisposable.add(disposable);
        //                log("onSubscribe观察者所在线程：" + Thread.currentThread().getName());
        //            }
        //
        //            @Override
        //            public void onNext(@NotNull Object o) {
        //                log("onNext观察者所在线程：" + Thread.currentThread().getName());
        //                Logs.log("收到的信息------" + o);
        ////                int index = (int) o;
        ////                if (index == 3) {
        ////                    disposable.dispose();
        ////                    Logs.log("取消了，发送足够了");
        ////                }
        //            }
        //
        //            @Override
        //            public void onError(Throwable e) {
        //                log("onError观察者所在线程：" + Thread.currentThread().getName());
        //            }
        //
        //            @Override
        //            public void onComplete() {
        //                Logs.log("这里表示已经执行完毕了");
        //                log("onComplete观察者所在线程：" + Thread.currentThread().getName());
        //            }
        //        };
        //
        //        observable
        //                .subscribeOn(Schedulers.io())
        //                .observeOn(AndroidSchedulers.mainThread())
        //                //observer运行在mainThread线程
        //                .subscribe(observer);//观察者和被观察者之间建立订阅关系
        //
        //        Consumer consumer = new Consumer() {
        //            @Override
        //            public void accept(Object o) throws Exception {
        //                Logs.log("消费者和被观察者之间建立订阅关系，这里表示已经执行完毕了：" + o);
        //            }
        //        };
        //        //消费者和被观察者之间建立订阅关系。这里关系被转换被生产者和消费者模式了。
        //        //无论是观察者模式还是生产/消费者模式，都遵循：onComplete()方法调用后，下游观察者都不在接受上游被观察者发送的数据。
        ////        observable.subscribe(consumer);
        //
        ////        disposable.dispose();
    }

    private Observer observerMap;
    private Observable<Map<String, Integer>> observableMap;

    CompositeDisposable comDisposable;
    private void btnRxJavaMap() {
        Observable observableStr = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {

            }
        });

        compositeDisposable.dispose();
        observableMap = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Logs.log("subscribe----1");
                emitter.onNext(2);
                Logs.log("subscribe----2");
                emitter.onNext(3);
                Logs.log("subscribe----3");
                emitter.onNext(4);
                Logs.log("subscribe----4");
            }
        }).map(new Function<Integer, Map<String, Integer>>() {
            @Override
            public Map<String, Integer> apply(@NotNull Integer o) throws Exception {
                Logs.log("发送过来的数据是：" + o);
                Map<String, Integer> map = new HashMap<>();
                map.put(String.valueOf(o), o);
                return map;
            }
        });

        observerMap = new Observer() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
                disposable = d;
                comDisposable = (CompositeDisposable) d;
                Logs.log("btnRxJavaMap----会首先执行这里吗？");
            }

            @Override
            public void onNext(@NotNull Object o) {
                if (o instanceof Map) {
                    Logs.log("收到的数据是一个map");
                }
                Logs.log("onNext()----" + o);
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Logs.log("发生了错误， 错误信息e===" + e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                Logs.log("这里表示已经执行完毕了");
            }
        };

        observableMap.subscribe(observerMap);
        disposable.dispose();
    }

    private void btnRxJavaZip() {
        Observable observable = Observable.zip(getStringObservable(), getIntegerObservable(), new BiFunction<String, String, Object>() {
            @NotNull
            @Override
            public Object apply(@NotNull String s, @NotNull String str) throws Exception {
                return s + "---->>>" + str;
            }
        });

        Observer observer = new Observer() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
                disposable = d;
                Logs.log("只要建立了订阅关系，这里就一定会执行的，而且只执行一次");
            }

            @Override
            public void onNext(@NotNull Object o) {
                Logs.log("来，我们瞧瞧，这是两个啥货色：" + o);
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Logs.log("error出错啦");
            }

            @Override
            public void onComplete() {
                Logs.log("执行完毕");
            }
        };
        observable.subscribe(observer);
    }

    private void btnRxJavaConcat() {
        Observable<String> observable = Observable.concat(Observable.just("苍苍", "齐云"), Observable.just("仕心", "飞渊"));
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                Logs.log("onSubscribe来一波");
            }

            @Override
            public void onNext(Object o) {
                Logs.log("合并成什么内容了：" + o);
            }

            @Override
            public void onError(Throwable e) {
                Logs.log("哎，又出错了");
            }

            @Override
            public void onComplete() {
                Logs.log("哈哈，完成了");
            }
        };
        observable.subscribe(observer);
    }

    private void btnRxJavaFlatMap() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("八式往复入轮回");
                emitter.onNext("自生而灭为天葬");
                emitter.onNext("极而复始，不死不灭为涅槃");
            }
        }).flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String o) throws Exception {
                String returnString = "";
                List<String> list = new ArrayList<>();
                if (o.startsWith("八")) {
                    for (int i = 0; i < 4; i++) {
                        returnString = "剑九-轮回" + o;
                        list.add(returnString);
                    }
                }
                if (o.startsWith("自")) {
                    for (int i = 0; i < 4; i++) {
                        returnString = "剑十-天葬" + o;
                        list.add(returnString);
                    }
                }
                if (o.startsWith("极")) {
                    for (int i = 0; i < 4; i++) {
                        returnString = "剑十一-涅槃" + o;
                        list.add(returnString);
                    }
                }
                return Observable.fromIterable(list).delay(500, TimeUnit.MILLISECONDS);
            }
        });

        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                Logs.log("onSubscribe来一波");
            }

            @Override
            public void onNext(Object o) {
                Logs.log("看看发送什么过来了：" + o);
            }

            @Override
            public void onError(Throwable e) {
                Logs.log("哎，又出错了" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Logs.log("哈哈，完成了");
            }
        };

        observable.subscribe(observer);
    }

    private void btnRxJavaConcatMap() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("八式往复入轮回");
                emitter.onNext("自生而灭为天葬");
                emitter.onNext("极而复始，不死不灭为涅槃");
            }
        }).concatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String o) throws Exception {
                String returnString = "";
                List<String> list = new ArrayList<>();
                if (o.startsWith("八")) {
                    for (int i = 0; i < 4; i++) {
                        returnString = "剑九-轮回" + o;
                        list.add(returnString);
                    }
                }
                if (o.startsWith("自")) {
                    for (int i = 0; i < 4; i++) {
                        returnString = "剑十-天葬" + o;
                        list.add(returnString);
                    }
                }
                if (o.startsWith("极")) {
                    for (int i = 0; i < 4; i++) {
                        returnString = "剑十一-涅槃" + o;
                        list.add(returnString);
                    }
                }
                return Observable.fromIterable(list).delay(500, TimeUnit.MILLISECONDS);
            }
        });

        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                Logs.log("onSubscribe来一波");
            }

            @Override
            public void onNext(Object o) {
                Logs.log("看看发送什么过来了：" + o);
            }

            @Override
            public void onError(Throwable e) {
                Logs.log("哎，又出错了" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Logs.log("哈哈，完成了");
            }
        };

        observable.subscribe(observer);
    }

    private void btnRxJavaDistinct() {
        Observable.just(1, 1, 2, 5, 2, 3, 2, 3, 4, 5)
                .distinct()
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Logs.log("开始啦");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Logs.log("去重之后的结果：" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logs.log("出错啦");
                    }

                    @Override
                    public void onComplete() {
                        Logs.log("完成了");
                    }
                });

        Observable.just(1, 1, 2, 5, 2, 3, 2, 3, 4, 5)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logs.log("去重之后的结果：" + integer);
                    }
                });
    }

    private void btnRxJavaFilter() {
        Observable<Integer> observable = Observable
                .just(1, 5, 8, 15, 20, 33, 22, 31, 24, 56)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer >= 20;
                    }
                });

        Consumer consumer = new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Logs.log("走Consumer的到的结果：" + o);
            }
        };

        observable.subscribe(consumer);
    }

    private void btnRxJavaBuffer() {
        Observable.just(1, 2, 8, 10, 15, 7, 12, 25, 30, 33)
                .buffer(3, 2)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(@NonNull List<Integer> integers) throws Exception {
                        Logs.log("integers.size()=======" + integers.size());
                        for (Integer i : integers) {
                            Logs.log("i===" + i);
                        }
                    }
                });
    }

    private void btnRxJavaTimer() {
        Observable<Long> observable = Observable.timer(2, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Consumer consumer = new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Toast.makeText(activity, "在consumer里面", Toast.LENGTH_SHORT).show();
            }
        };

        observable.subscribe(consumer);
    }

    private Disposable mConsumerDisposable;
    private int ionsumer = 0;

    private void btnRxJavaInterval() {
        final long delay = 1500;
        final long period = 1000;

        //Schedulers中5中线程的区别————OK
        //interval()4个重载方法。————OK
        //subscribe()方法的重载
        //Flowable被压的功用
        //        Observable<Long> observable = Observable
        //                .interval(delay, period, TimeUnit.MILLISECONDS)
        //                .subscribeOn(Schedulers.newThread())
        //                .observeOn(AndroidSchedulers.mainThread());
        //
        //        Consumer consumer = new Consumer() {
        //            @Override
        //            public void accept(Object o) throws Exception {
        //                if (ionsumer > 20) {
        //                    mConsumerDisposable.dispose();
        //                }
        //                final String content = delay + "天后结婚，每然后隔" + period + "天就要做一回";
        //                Logs.log(content);
        //                ionsumer++;
        //            }
        //        };
        mConsumerDisposable = Observable
                .interval(delay, period, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (ionsumer > 20) {
                            mConsumerDisposable.dispose();
                        }
                        final String content = delay + "天后结婚，每然后隔" + period + "天就要做一回";
                        Logs.log(content);
                        ionsumer++;
                    }
                });
        //        mConsumerDisposable = observable.subscribe(consumer);
    }

    private void btnRxJavaDoOnNext() {
        final long delay = 1500;
        final long period = 1000;

        //Schedulers中5中线程的区别，
        //interval()4个重载方法。
        //subscribe()方法的重载
        mConsumerDisposable = Observable
                .interval(delay, period, TimeUnit.MILLISECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        log("在每次make love之前，还是做足前戏，这样才能更好的进入状态");
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Map<Long, String>>() {
                    @Override
                    public Map<Long, String> apply(@NonNull Long aLong) throws Exception {
                        Map<Long, String> map = new HashMap<>();
                        map.put(aLong, aLong + "");
                        return map;
                    }
                })
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (ionsumer > 20) {
                            mConsumerDisposable.dispose();
                        }
                        final String content = delay + "天后结婚，每然后隔" + period + "天就要做一回";
                        Logs.log(content);
                        ionsumer++;
                    }
                });
    }

    private void btnRxJavaSkip() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).skip(3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logs.log("接受情况是怎么样的额？" + integer);
                    }
                });
    }

    private void btnRxJavaTake() {
        final long count = 5;
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).take(count)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logs.log("据说是最多接收" + count + "个？" + integer);
                    }
                });
    }

    private void btnRxJavaSingleObserver() {
        Single.just(1)
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Logs.log("Single---SingleObserver只重写了onSubscribe，onSuccess，onError三个方法，" +
                                "其中onSuccess代替了onNext方法，complete方法没有复写");
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Logs.log("成功了");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logs.log("出错了");
                    }
                });
    }

    private void btnRxJavaDebounce() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Thread.sleep(850);
                emitter.onNext(2);
                Thread.sleep(905);
                emitter.onNext(3);
                Thread.sleep(500);
                emitter.onNext(4);
                Thread.sleep(705);
                emitter.onNext(5);
                Thread.sleep(10);
                emitter.onNext(6);
                Thread.sleep(810);
                emitter.onComplete();
            }
        })
                .debounce(800, TimeUnit.MILLISECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logs.log("接收到了这些数据：" + integer);
                    }
                });
    }

    private void btnRxJavaDefer() {
        Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.just(1, 2, 3);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logs.log("defer : " + integer + "\n");
            }
        });
    }

    private void btnRxJavaLast() {
        Observable.just(1, 2, 3, 4)
                .last(6)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Logs.log("获取到的是什么数据？" + integer);
                    }
                });
    }

    private void btnRxJavaMerge() {
        Map<String, String> map1 = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
        map1.put("1", "1");
        map1.put("2", "2");
        map1.put("3", "3");
        map2.put("4", "4");
        map2.put("5", "5");
        map2.put("6", "6");


        Observable.merge(Observable.just(map1), Observable.just(map2))
                .subscribe(new Consumer<Map<String, String>>() {
                    @Override
                    public void accept(Map<String, String> map) throws Exception {
                        Logs.log("合并后是什么呀的？" + map);
                    }
                });
    }

    private void btnRxJavaReduce() {
        Observable.just(1, 2, 3, 4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        Logs.log("integer==" + integer + "      integer2===" + integer2);
                        return integer * integer2;
                    }
                }).subscribe(new MaybeObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {
                Logs.log("看看是什么鬼？" + integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void btnRxJavaScan() {
        Observable.just(1, 2, 3, 4)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        Logs.log("integer==" + integer + "      integer2===" + integer2);
                        return integer * integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logs.log("看看是什么鬼？" + integer);
            }
        });
    }

    private Observable<String> getStringObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext("任缥缈");
                    Logs.log("String emit : 任缥缈 \n");
                    e.onNext("独眼龙");
                    Logs.log("String emit : 独眼龙 \n");
                    e.onNext("苍越孤鸣");
                    Logs.log("String emit : 苍越孤鸣 \n");
                    e.onNext("颢天玄宿");
                    Logs.log("String emit : 颢天玄宿 \n");
                    e.onNext("天剑慕容府");
                    Logs.log("String emit : 天剑慕容府 \n");
                }
            }
        });
    }

    private Observable<String> getIntegerObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext("秋水浮萍");
                    Logs.log("Integer emit : 秋水浮萍 \n");
                    e.onNext("荒野金刀");
                    Logs.log("Integer emit : 荒野金刀 \n");
                    e.onNext("皇世惊天宝典");
                    Logs.log("Integer emit : 皇世惊天宝典 \n");
                    e.onNext("浩星归流");
                    Logs.log("Integer emit : 浩星归流 \n");
                }
            }
        });
    }

    private Observable<Boolean> getBooleanObservable() {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(true);
                    Logs.log("Integer emit : true \n");
                    e.onNext(false);
                    Logs.log("Integer emit : false \n");
                    e.onNext(true);
                    Logs.log("Integer emit : true \n");
                    e.onNext(false);
                    Logs.log("Integer emit : false \n");
                }
            }
        });
    }

    FileReader fileReader;
}
