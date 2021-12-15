package com.mydemo.toolslist.user_defined_ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mydemo.toolslist.R;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * @author: Nixon
 * @ProjectName: ToolsList
 * @Package: com.mydemo.toolslist.user_defined_ui
 * @ClassName: VideoView
 * @CreateDate: 2021/5/28 13:36
 * @Description: 本类作用描述：
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
public class  VideoView extends View {
    Context mContext;
    LayoutInflater inflater;
    Paint paint;
    Path path;
    RectF rectF;


    public VideoView(Context context) {
        super(context);
        mContext = context;
    }

    public VideoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public VideoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public VideoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
    }

    public void initParams(@Nullable AttributeSet attrs){
        inflater = LayoutInflater.from(mContext);
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.VideoViewAttr);
        boolean mIsNeedCoverLayout = ta.getBoolean(R.styleable.VideoViewAttr_is_need_cover_layout, false);
        boolean mIsNeedTopBar = ta.getBoolean(R.styleable.VideoViewAttr_is_need_top_bar, false);
        boolean mIsNeedDefaultProgressBar = ta.getBoolean(R.styleable.VideoViewAttr_is_need_default_progress_bar, false);
        boolean mIsNeedPlayProgressBar = ta.getBoolean(R.styleable.VideoViewAttr_is_need_play_progress_bar, true);
        boolean mIsFullscreen = ta.getBoolean(R.styleable.VideoViewAttr_is_fullscreen, false);
        int mCoverLayoutPaddingLeft = (int) ta.getDimension(R.styleable.VideoViewAttr_cover_layout_padding_left, 0);
        int mCoverLayoutPaddingRight = (int) ta.getDimension(R.styleable.VideoViewAttr_cover_layout_padding_right, 0);

        int defualtColor = ta.getColor(R.styleable.VideoViewAttr_defualt_bg_Color, 0);
        int defualtProgress = ta.getInt(R.styleable.VideoViewAttr_defualt_progress, 0);

        ta.recycle();
        ViewGroup mRootView = (RelativeLayout) View.inflate(mContext, R.layout.item_video_root_layout, null);
        ConstraintLayout mView = (ConstraintLayout) LayoutInflater.from(mContext).inflate(R.layout.item_video_root_layout, mRootView, false);

        mView.requestFocus(0);
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (mIsNeedCoverLayout) {
            initVideoCoverViews();
        } else {
            initAddPlayView();
        }
    }

    private void initVideoCoverViews(){

    }

    private void initAddPlayView(){

    }

    @Override
    public void setFitsSystemWindows(boolean fitSystemWindows) {
        super.setFitsSystemWindows(fitSystemWindows);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }
}
