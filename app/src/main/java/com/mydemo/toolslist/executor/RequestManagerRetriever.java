package com.mydemo.toolslist.executor;//package threadpool;
//
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.app.Application;
//import android.content.Context;
//import android.content.ContextWrapper;
//import android.os.Build;
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.util.Log;
//
//import com.bumptech.glide.RequestManager;
//import com.bumptech.glide.manager.ApplicationLifecycle;
//import com.bumptech.glide.manager.EmptyRequestManagerTreeNode;
//import com.bumptech.glide.manager.RequestManagerFragment;
//import com.bumptech.glide.manager.SupportRequestManagerFragment;
//import com.bumptech.glide.util.Util;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 作者：Created by chen1 on 2020/4/20.
// * 邮箱：chen126jie@163.com
// * TODO：
// */
//public class RequestManagerRetriever implements Handler.Callback {
//    private static final String TAG = "RMRetriever";
//    static final String FRAGMENT_TAG = "com.bumptech.glide.manager";
//
//    private static final RequestManagerRetriever INSTANCE = new RequestManagerRetriever();
//    private static final int ID_REMOVE_FRAGMENT_MANAGER = 1;
//    private static final int ID_REMOVE_SUPPORT_FRAGMENT_MANAGER = 2;
//    private volatile RequestManager applicationManager;
//    final Map<android.app.FragmentManager, RequestManagerFragment> pendingRequestManagerFragments = new HashMap<>();
//    final Map<FragmentManager, SupportRequestManagerFragment> pendingSupportRequestManagerFragments = new HashMap<>();
//    private final Handler handler;
//    public static com.bumptech.glide.manager.RequestManagerRetriever get() {
//        return INSTANCE;
//    }
//    RequestManagerRetriever() {
//        handler = new Handler(Looper.getMainLooper(), this);
//    }
//
//    private RequestManager getApplicationManager(Context context) {
//        if (applicationManager == null) {
//            synchronized (this) {
//                if (applicationManager == null) {
//                    applicationManager = new RequestManager(context.getApplicationContext(),new ApplicationLifecycle(), new EmptyRequestManagerTreeNode());
//                }
//            }
//        }
//        return applicationManager;
//    }
//
//    public RequestManager get(Context context) {
//        if (context == null) {
//            throw new IllegalArgumentException("You cannot start a load on a null Context");
//        } else if (Util.isOnMainThread() && !(context instanceof Application)) {
//            if (context instanceof FragmentActivity) {
//                return get((FragmentActivity) context);
//            } else if (context instanceof Activity) {
//                return get((Activity) context);
//            } else if (context instanceof ContextWrapper) {
//                return get(((ContextWrapper) context).getBaseContext());
//            }
//        }
//        return getApplicationManager(context);
//    }
//
//    public RequestManager get(FragmentActivity activity) {
//        if (Util.isOnBackgroundThread()) {
//            return get(activity.getApplicationContext());
//        } else {
//            assertNotDestroyed(activity);
//            FragmentManager fm = activity.getSupportFragmentManager();
//            return supportFragmentGet(activity, fm);
//        }
//    }
//
//    public RequestManager get(Fragment fragment) {
//        if (fragment.getActivity() == null) {
//            throw new IllegalArgumentException("You cannot start a load on a fragment before it is attached");
//        }
//        if (Util.isOnBackgroundThread()) {
//            return get(fragment.getActivity().getApplicationContext());
//        } else {
//            FragmentManager fm = fragment.getChildFragmentManager();
//            return supportFragmentGet(fragment.getActivity(), fm);
//        }
//    }
//
//    public RequestManager get(Activity activity) {
//        if (Util.isOnBackgroundThread() || Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
//            return get(activity.getApplicationContext());
//        } else {
//            assertNotDestroyed(activity);
//            android.app.FragmentManager fm = activity.getFragmentManager();
//            return fragmentGet(activity, fm);
//        }
//    }
//
//    private static void assertNotDestroyed(Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
//            throw new IllegalArgumentException("You cannot start a load for a destroyed activity");
//        }
//    }
//
//    public RequestManager get(android.app.Fragment fragment) {
//        if (fragment.getActivity() == null) {
//            throw new IllegalArgumentException("You cannot start a load on a fragment before it is attached");
//        }
//        if (Util.isOnBackgroundThread() || Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            return get(fragment.getActivity().getApplicationContext());
//        } else {
//            android.app.FragmentManager fm = fragment.getChildFragmentManager();
//            return fragmentGet(fragment.getActivity(), fm);
//        }
//    }
//
//    RequestManagerFragment getRequestManagerFragment(final android.app.FragmentManager fm) {
//        RequestManagerFragment current = (RequestManagerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
//        if (current == null) {
//            current = pendingRequestManagerFragments.get(fm);
//            if (current == null) {
//                current = new RequestManagerFragment();
//                pendingRequestManagerFragments.put(fm, current);
//                fm.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss();
//                handler.obtainMessage(ID_REMOVE_FRAGMENT_MANAGER, fm).sendToTarget();
//            }
//        }
//        return current;
//    }
//
//    RequestManager fragmentGet(Context context, android.app.FragmentManager fm) {
//        RequestManagerFragment current = getRequestManagerFragment(fm);
//        RequestManager requestManager = current.getRequestManager();
//        if (requestManager == null) {
//            requestManager = new RequestManager(context, current.getLifecycle(), current.getRequestManagerTreeNode());
//            current.setRequestManager(requestManager);
//        }
//        return requestManager;
//    }
//
//    SupportRequestManagerFragment getSupportRequestManagerFragment(final FragmentManager fm) {
//        SupportRequestManagerFragment current = (SupportRequestManagerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
//        if (current == null) {
//            current = pendingSupportRequestManagerFragments.get(fm);
//            if (current == null) {
//                current = new SupportRequestManagerFragment();
//                pendingSupportRequestManagerFragments.put(fm, current);
//                fm.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss();
//                handler.obtainMessage(ID_REMOVE_SUPPORT_FRAGMENT_MANAGER, fm).sendToTarget();
//            }
//        }
//        return current;
//    }
//
//    RequestManager supportFragmentGet(Context context, FragmentManager fm) {
//        SupportRequestManagerFragment current = getSupportRequestManagerFragment(fm);
//        RequestManager requestManager = current.getRequestManager();
//        if (requestManager == null) {
//            requestManager = new RequestManager(context, current.getLifecycle(), current.getRequestManagerTreeNode());
//            current.setRequestManager(requestManager);
//        }
//        return requestManager;
//    }
//
//    @Override
//    public boolean handleMessage(Message message) {
//        boolean handled = true;
//        switch (message.what) {
//            case ID_REMOVE_FRAGMENT_MANAGER:
//                break;
//            case ID_REMOVE_SUPPORT_FRAGMENT_MANAGER:
//                break;
//            default:
//                handled = false;
//        }
//        return handled;
//    }
//}
