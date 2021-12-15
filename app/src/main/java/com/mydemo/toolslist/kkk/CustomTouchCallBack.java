package com.mydemo.toolslist.kkk;

import android.graphics.Canvas;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 作者：Created by chen1 on 2020/4/12.
 * 邮箱：chen126jie@163.com
 * TODO：RecyclerView的item拖拽滑动的方法
 */
public class CustomTouchCallBack extends ItemTouchHelper.Callback {

//    private ItemHelperListener mListener;
//
//    public CustomTouchCallBack(ItemHelperListener listener) {
//        mListener = listener;
//    }

    /*这个方法用于让RecyclerView拦截向上滑动，向下滑动，想左滑动*/
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT);
    }

    /**
     * drag状态下，在canDropOver()返回true时，会调用该方法让我们拖动换位置的逻辑(需要自己处理变换位置的逻辑)
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//        if (mListener != null) {
//            mListener.onSwap(viewHolder.getAdapterPosition(), target.getAdapterPosition());
//        }
        return true;
    }

    /**
     * 针对drag状态，当前target对应的item是否允许移动
     * 我们一般用drag来做一些换位置的操作，就是当前对应的target对应的Item可以移动
     */
    @Override
    public boolean canDropOver(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder current, @NonNull RecyclerView.ViewHolder target) {
        return super.canDropOver(recyclerView, current, target);
    }

    /**
     * 针对drag状态，当drag ItemView跟底下ItemView重叠时，可以给drag ItemView设置一个Margin值
     * 让重叠不容易发生，相当于增大了drag Item的区域
     */
    @Override
    public int getBoundingBoxMargin() {
        return super.getBoundingBoxMargin();
    }

    /**
     * 针对drag状态，当滑动超过多少就可以出发onMove()方法(这里指onMove()方法的调用，并不是随手指移动的View)
     */
    @Override
    public float getMoveThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return super.getMoveThreshold(viewHolder);
    }

    /**
     * 针对drag状态，在drag的过程中获取drag itemView底下对应的ViewHolder(一般不用我们处理直接super就好了)
     */
    @Override
    public RecyclerView.ViewHolder chooseDropTarget(@NonNull RecyclerView.ViewHolder selected, @NonNull List<RecyclerView.ViewHolder> dropTargets, int curX, int curY) {
        return super.chooseDropTarget(selected, dropTargets, curX, curY);
    }

    /**
     * 当onMove return true的时候调用(一般不用我们自己处理，直接super就好)
     */
    @Override
    public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
    }

    /**
     * 针对swipe和drag状态，当一个item view在swipe、drag状态结束的时候调用
     * drag状态：当手指释放的时候会调用
     * swipe状态：当item从RecyclerView中删除的时候调用，一般我们会在onSwiped()函数里面删除掉指定的item view
     */
    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
    }

    /**
     * 针对swipe和drag状态，整个过程中一直会调用这个函数,随手指移动的view就是在super里面做到的(和ItemDecoration里面的onDraw()函数对应)
     */
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            float alpha = 1 - Math.abs(dX) / viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setScaleX(alpha);
            viewHolder.itemView.setScaleY(alpha);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    /**
     * 针对swipe和drag状态，整个过程中一直会调用这个函数(和ItemDecoration里面的onDrawOver()函数对应)
     * 这个函数提供给我们可以在RecyclerView的上面再绘制一层东西，比如绘制一层蒙层啥的
     */
    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    /**
     * 针对swipe和drag状态，当手指离开之后，view回到指定位置动画的持续时间(swipe可能是回到原位，也有可能是swipe掉)
     */
    @Override
    public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
    }


    /**
     * 针对drag状态，当itemView滑动到RecyclerView边界的时候(比如下面边界的时候),RecyclerView会scroll，
     * 同时会调用该函数去获取scroller距离(不用我们处理 直接super)
     */
    @Override
    public int interpolateOutOfBoundsScroll(@NonNull RecyclerView recyclerView, int viewSize, int viewSizeOutOfBounds, int totalSize, long msSinceStartScroll) {
        return super.interpolateOutOfBoundsScroll(recyclerView, viewSize, viewSizeOutOfBounds, totalSize, msSinceStartScroll);
    }

    /**
     * 针对swipe和drag状态，当swipe或者drag对应的ViewHolder改变的时候调用
     * 我们可以通过重写这个函数获取到swipe、drag开始和结束时机，viewHolder 不为空的时候是开始，空的时候是结束
     */
    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 针对swipe状态，是否允许swipe(滑动)操作
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
    }

    /**
     * 针对swipe状态，swipe滑动的位置超过了百分之多少就消失
     */
    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return 0.5f;
    }

    /**
     * 针对swipe状态，swipe的逃逸速度，换句话说就算没达到getSwipeThreshold设置的距离，达到了这个逃逸速度item也会被swipe消失掉
     */
    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return defaultValue;
    }

    /**
     * 针对swipe状态，swipe滑动的阻尼系数,设置最大滑动速度
     */
    @Override
    public float getSwipeVelocityThreshold(float defaultValue) {
        return defaultValue;
    }

    /**
     * 针对swipe状态，swipe 到达滑动消失的距离回调函数,一般在这个函数里面处理删除item的逻辑
     * 确切的来讲是swipe item滑出屏幕动画结束的时候调用
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//        if (mListener != null) {
//            mListener.onRemove(viewHolder.getAdapterPosition());
//        }
    }
}

