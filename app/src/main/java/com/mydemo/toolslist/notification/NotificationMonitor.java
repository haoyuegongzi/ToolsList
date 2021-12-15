//package com.mydemo.toolslist.notification;
//
//import android.annotation.SuppressLint;
//import android.service.notification.NotificationListenerService;
//import android.service.notification.StatusBarNotification;
//import android.util.Log;
//import android.widget.Toast;
//
//
///**
// * 作者：Nixon
// * date：2020/8/17.
// * 邮箱：jan.romon@gmail.com
// * TODO：
// */
//@SuppressLint("OverrideAbstract")
//public class NotificationMonitor extends NotificationListenerService {
//    public static String SEND_MSG_BROADCAST = "notify_msg";
//    //获取通知栏信息
//    @Override
//    public void onNotificationPosted(StatusBarNotification sbn) {
//        super.onNotificationPosted(sbn);
//
//        //异常捕捉，防止sbn数据凋谢
//        try {
//            //监听任务栏数据不为空
//            if (sbn != null) {
//                //获取厂家
//                String cj = sbn.getPackageName();
//                //获取微信、支付宝标题和收款金额
//                String bt_je = (sbn.getNotification().tickerText).toString();
//                //获取微信、支付宝标题
//                String bt = (sbn.getNotification().extras.get("android.title")).toString();
//                //获取微信、支付宝条数
//                String ts = (sbn.getNotification().extras.get("android.text")).toString();
//                //输出run控制台打印
//                Log.i("a", cj);
//                Log.i("b", bt_je);
//                Log.i("c", bt);
//                Log.i("d", ts);
//
//                //                //过滤其他垃圾通知，只保留微信与支付宝
//                //                if (bt.contains("微信支付") || bt.contains("收款通知")) {
//                //                    //提取字符串里的整数与小数点
//                //                    //Pattern p = Pattern.compile("\\d+");
//                //                    Pattern p = Pattern.compile("(([1-9]\\d*)|0)(\\.(\\d){0,2})?");
//                //                    Matcher m = p.matcher(bt_je);
//                //                    m.find();
//                //                    //金额参数
//                //                    String amount = m.group();
//                //                    //厂家标题
//                //                    String title = bt.toString();
//                //                    Log.i("提取数字", amount);
//                //
//                //                    //请求url接口地址方法
//                //                    String url = ServiceUrls.getReserveMethodUrl("MonitorDataProcessing");
//                //                    //请求参数
//                //                    Map<String, Object> map = new HashMap<>();
//                //                    map.put("Amount", amount);
//                //                    map.put("Title", title);
//                //
//                //                    //发送网络请求
//                //                    OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
//                //                        @Override
//                //                        public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
//                //                            new Thread() {
//                //                                public void run() {
//                //                                    String strText = "网络环境不佳，请稍后再试";
//                //                                    if (isSuccess && responseCode == 200) {
//                //                                        try {
//                //                                            JSONObject jsonObject = new JSONObject(response);
//                //                                            int code = jsonObject.getInt("code");
//                //                                            strText = jsonObject.getString("text");
//                //                                            if (code == 200) {
//                //                                                strText = "成功监听一条数据";
//                //                                            } else {
//                //                                                strText = "监听失败";
//                //                                            }
//                //                                        } catch (JSONException e) {
//                //                                            e.printStackTrace();
//                //                                        }
//                //                                    }
//                //                                    //解决在子线程中调用Toast的异常情况处理
//                //                                    Looper.prepare();
//                //                                    Toast.makeText(NotificationMonitor.this, strText, Toast.LENGTH_LONG).show();
//                //                                    Looper.loop();
//                //                                }
//                //                            }.start();
//                //                        }
//                //                    });//网络请求end
//                //
//                //                } else {
//                //                    new Thread() {
//                //                        @Override
//                //                        public void run() {
//                //                            //解决在子线程中调用Toast的异常情况处理
//                //                            Looper.prepare();
//                //                            Toast.makeText(NotificationMonitor.this, "监听到垃圾数据，服务器已过滤", Toast.LENGTH_LONG).show();
//                //                            Looper.loop();
//                //                        }
//                //                    }.start();
//                //                }
//            } else {
//                Toast.makeText(this, "sbn为空", Toast.LENGTH_LONG).show();
//                Log.i("sbn异常", "sbn为空");
//            }
//        } catch (Exception e) {
//            //防止程序奔溃
//            Log.i("程序异常", e.toString());
//        }
//    }
//}
