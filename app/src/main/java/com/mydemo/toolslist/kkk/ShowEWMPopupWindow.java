package com.mydemo.toolslist.kkk;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.mydemo.toolslist.R;

/**
 * 扫码 Popwindow窗口
 * @author hutian 2018-08-18
 *
 */
public class ShowEWMPopupWindow extends PopupWindow {

    private Context mContext;
    // 提示语
    private ImageView ewm;
    private RelativeLayout ewm_layout;
    private Bitmap bm;

    public ShowEWMPopupWindow(Activity context, Bitmap bm) {
        super(context);
        this.mContext = context;
        this.bm = bm;
        initPopwindow();
    }

    public ShowEWMPopupWindow(Context context) {
        super(context);
        mContext = context;
        initPopwindow();
    }

    private void initPopwindow() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popwindow_showewm, null);
        setContentView(view);
        setFocusable(true);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        ewm = (ImageView) view.findViewById(R.id.ewm);
        ewm_layout = (RelativeLayout) view.findViewById(R.id.ewm_layout);
        this.setOutsideTouchable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        this.setAnimationStyle(R.style.pop_fade_show);
//		// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
//		Drawable drawable=mContext.getResources().getDrawable(R.drawable.sao_pop_bg);
        this.setBackgroundDrawable(dw);
        ewm = (ImageView) view.findViewById(R.id.ewm);
        ewm_layout = (RelativeLayout) view.findViewById(R.id.ewm_layout);
        ewm.setImageBitmap(bm);
        ewm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v);
            }
        });

    }

    //展示Popwindow的位置
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            Animation ImageRotateAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
            ewm.setAnimation(ImageRotateAnimation);
            backgroundAlpha((Activity)mContext, 1f);
            this.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        } else {
            Animation ImageRotateAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade);
            ewm.setAnimation(ImageRotateAnimation);
            this.dismiss();
        }
    }
    //背景的改变透明度  从而达到“变暗”的效果
    public void backgroundAlpha(Activity activity, float bgAlpha) {
        if (activity != null && !activity.isFinishing()) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = bgAlpha;
            activity.getWindow().setAttributes(lp);
        }
    }

    public void refreshCode(Bitmap bm) {
        this.bm = bm;
        ewm.setImageBitmap(bm);
    }
}
