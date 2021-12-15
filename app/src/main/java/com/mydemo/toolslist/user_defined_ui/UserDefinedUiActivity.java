package com.mydemo.toolslist.user_defined_ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mydemo.toolslist.BaseActivity;
import com.mydemo.toolslist.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class UserDefinedUiActivity extends BaseActivity {
    ConstraintLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_defined_ui);
        testAttachToRoot();
        OnClickableChangeListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private void testAttachToRoot() {
        LayoutInflater inflater = LayoutInflater.from(this);
        rootView = findViewById(R.id.rootLayoutView);

        //        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.item_page, rootView, true);
        //root != null 时调用addView()方法，会抛异常：java.lang.IllegalStateException:
        // The specified child already has a parent. You must call removeView() on the child's parent first.
        //        ViewGroup childView = (ViewGroup) inflater.inflate(R.layout.item_page, null);
        AppCompatTextView childView = (AppCompatTextView) inflater.inflate(R.layout.child_view, null);
        rootView.addView(childView);

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dp2px(5), dp2px(5), dp2px(5), dp2px(5));

        childView.setLayoutParams(layoutParams);

        //        RelativeLayout container = viewGroup.findViewById(R.id.container);
        //        AppCompatTextView tvTitle = childView.findViewById(R.id.tvTitle);
        childView.setText("墨家钜子俏如来太菜了，估计莫教授会气得从棺材板下跳出来。。。");
        childView.setTextColor(Color.parseColor("#ff0000"));
        childView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);

    }

    Intent intent = getIntent();

    private void OnClickableChangeListener() {
        Button btnClick = findViewById(R.id.btnClick);

        Context context = btnClick.getContext();
        if (context instanceof UserDefinedUiActivity) {
            loge("context instanceof UserDefinedUiActivity：" + true);
        }
        UserDefinedUiActivity userActivity = (UserDefinedUiActivity) btnClick.getContext();


        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loge("OnClickListener的onClick()方法");
                // Toast.makeText(activity, "View的clickable=false状态下设置点击事件，点击事件是可以生效的", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onClick: View的clickable=false状态下设置点击事件，点击事件是可以生效的");
                Log.e(TAG, "onClick: btnClick===v：" + (btnClick == v));
                Log.e(TAG, "onClick: btnClick===v：" + (btnClick.equals(v)));
                Toast.makeText(userActivity, "这个强转", Toast.LENGTH_SHORT).show();
                // 入参为正：内容向左、上方向滚动；入参为负：内容向右、下方向滚动
                // 这个方法滚动/滑动的仅仅是View里面显示的内容content。View控件本身的位置是不会变化的，就如RecyclerView和NestedScrollView，
                // 滑动/滚动的是其内部的子View（他们的子View就相当于这里的content），而其本身的位置不变。如果是单纯的view如：TextView滑动/滚动
                // 的是其内部的文本、ImageView滑动/滚动的是其内部的Image图。本质原理上是View的onDraw方法在绘制时，坐标位置发生了变化。
                // scrollTo(int x, int y)是滚动到(x, y)座标位置去；scrollBy(int x, int y)是滚动(x, y)这个距离。
                btnClick.scrollTo(-50, -20);
                // btnClick.scrollBy(-50, -20);
            }
        });

        btnClick.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                loge("OnTouchListener的onTouch()方法");
                Log.e(TAG, "onTouch: Enabled == false：" + " 点击事件OnClickListener和触摸事件OnTouchListener的有效性");
                return false;
            }
        });

        // View宽度不是match_parent时生效。
        // btnClick.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        // btnClick.setAlpha(0.6f);
        // btnClick.setTransitionAlpha(0.6f);
        // btnClick.setForceDarkAllowed(true);

        // btnClick.setTop(120);
        //
        // Drawable background = btnClick.getBackground();
        //
        // Bitmap bitmap;
        // Canvas canvas = new Canvas();//bitmap
        // canvas.drawColor(0xffffff00);
        // background.draw(canvas);
        // btnClick.setBackground(background);
        // btnClick.invalidate();
        // btnClick.setOutlineSpotShadowColor(Color.BLACK);

        // btnClick.setEnabled(false);
        // btnClick.setClickable(false);
        // boolean isAttachedToWindow = btnClick.isAttachedToWindow();
        // Log.e(TAG, "onTouch: isAttachedToWindow：" + isAttachedToWindow);
        // btnClick.setFocusable(true);
        // btnClick.setPressed(true);
        // ArrayList<View> viewArrayList = rootView.getTouchables();
        // for (int i = 0; i < viewArrayList.size(); i++) {
        //     Log.e(TAG, "OnClickableChangeListener: " + viewArrayList.get(i).getClass().getName());
        // }

        // loge("" + (btnClick.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL));

        // rootView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
        //     @Override
        //     public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //         loge("onScrollChange----->>>scrollX====" + scrollX);
        //         loge("onScrollChange----->>>scrollY====" + scrollY);
        //         loge("onScrollChange----->>>oldScrollX====" + oldScrollX);
        //         loge("onScrollChange----->>>oldScrollY====" + oldScrollY);
        //     }
        // });


        // ImageView imageView;

        // Matrix matrix = rootView.getMatrix();
        // Matrix mMatrix = rootView.getAnimationMatrix();

        intent.getComponent();

    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            return false;
        }
    });


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        loge("执行了dispatchTouchEvent()方法");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        loge("onTouchEvent()方法");
        return super.onTouchEvent(ev);
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        loge("event.getAction()===" + event.getAction());
        loge("event.getKeyCode()===" + event.getKeyCode());


        return super.dispatchKeyEvent(event);
    }


}