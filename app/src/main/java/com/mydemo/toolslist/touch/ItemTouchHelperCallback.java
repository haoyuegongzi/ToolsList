package com.mydemo.toolslist.touch;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mydemo.toolslist.R;
import com.mydemo.toolslist.touch.ItemTouchHelperAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author: Nixon
 * @ProjectName: A3-SMS
 * @Package: com.mobile.automatic.ui.activity.home
 * @ClassName: ItemTouchHelperCallback
 * @CreateDate: 2021/3/2 9:07
 * @Description: 本类作用描述：
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private static final String TAG = "ItemTouchHelperCallback";
    private Callback helperCallback;
    private ItemTouchHelperAdapter adapter;

    public ItemTouchHelperCallback(Callback helperCallback) {
        this.helperCallback = helperCallback;
    }

    public ItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * 设置滑动类型标记，指定拖拽和滑动在哪个方向是被允许的
     *
     * @param recyclerView：被操作的recyclerView列表
     * @param viewHolder：被操作的item对象
     * @return 返回一个整数类型的标识，用于判断Item那种移动行为是允许的
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.e(TAG, "getMovementFlags: ");
        //拖拽：上下左右四个方向都可以拖拽
        int directions = (ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        //ItemTouchHelper.ACTION_STATE_DRAG：表示拖拽，
        int dragFlag = makeFlag(ItemTouchHelper.ACTION_STATE_DRAG, directions);

        //左右滑动：左右两个方向可以滑动
        int swipeFlags = ItemTouchHelper.LEFT;
//        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        // ItemTouchHelper.ACTION_STATE_SWIPE：表示左右滑动;
        // ItemTouchHelper.ACTION_STATE_IDLE：表示不响应对item的滑动操作
        int swipeFlag = makeMovementFlags(ItemTouchHelper.ACTION_STATE_SWIPE, swipeFlags);
        // makeMovementFlags方法：第一个参数用来指定拖动，第二个参数用来指定滑动
        return makeMovementFlags(dragFlag, swipeFlag);
    }

    //TODO：默认情况下是支持拖动和滑动的，也就是isLongPressDragEnabled()和isItemViewSwipeEnabled()是返回true。
    //TODO：实战中根据项目需求考虑使用滑动还是拖拽。

    /**
     * 是否支持长按拖动。该方法如果返回true，onMove()无论返回true还是false，RecyclerView都不支持长按拖拽item。
     *
     * @return false：不支持长按操作
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return super.isLongPressDragEnabled();
//        return false;
    }

    /**
     * 拖拽切换Item的回调，该方法如果返回true表示切换了位置，反之返回false
     *
     * @param recyclerView：被操作的recyclerView列表
     * @param viewHolder：拖动的Item
     * @param target：拖动的目标位置的Item
     * @return 如果Item切换了位置，返回true；反之，返回false
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.e(TAG, "onMove: ");
        helperCallback.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }


    /**
     * Item是否支持滑动，true：支持滑动操作，false：不支持滑动操作。
     * 该方法如果返回true，onSwiped()无论返回true还是false，RecyclerView都不支持滑动删除item。
     *
     * @return false 不支持滑动操作
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
    }

    /**
     * 该方法为Item滑动回调
     *
     * @param viewHolder：滑动的item
     * @param direction：Item滑动的方向
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.e(TAG, "onSwiped: ");
        helperCallback.onItemDelete(viewHolder.getAdapterPosition());
    }

    /**
     * Item被选中时候回调
     *
     * @param viewHolder：滑动的item
     * @param actionState        当前Item的状态
     *                           ItemTouchHelper.ACTION_STATE_IDLE   闲置状态
     *                           ItemTouchHelper.ACTION_STATE_SWIPE  滑动中状态
     *                           ItemTouchHelper#ACTION_STATE_DRAG   拖拽中状态
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 删除的过程中增加一个动画，移动过程中绘制Item的回调
     *
     * @param c
     * @param recyclerView：被操作的列表recyclerView
     * @param viewHolder：被操作的item对象
     * @param dX：X轴移动的距离
     * @param dY：Y轴移动的距离
     * @param actionState：当前Item的状态
     * @param isCurrentlyActive：如果当前被用户操作为true，反之为false
     */
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        //滑动时自己实现背景及图片
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //dX大于0时向右滑动，小于0向左滑动
            //获取滑动的view
            View itemView = viewHolder.itemView;
            Resources resources = recyclerView.getContext().getResources();
            //获取删除指示的背景图片
            Bitmap bitmap = BitmapFactory.decodeResource(resources, R.mipmap.delete);
            int padding = 10;//图片绘制的padding
            int maxDrawWidth = 2 * padding + bitmap.getWidth();//最大的绘制宽度
            Paint paint = new Paint();
            paint.setColor(resources.getColor(R.color.common_blue_light));
            int x = Math.round(Math.abs(dX));
            //实际的绘制宽度，取实时滑动距离x和最大绘制距离maxDrawWidth最小值
            int drawWidth = Math.min(x, maxDrawWidth);
            //绘制的top位置
            int itemTop = itemView.getBottom() - itemView.getHeight();
            //向右滑动
            if (dX > 0) {
                //根据滑动实时绘制一个背景
                c.drawRect(itemView.getLeft(), itemTop, drawWidth, itemView.getBottom(), paint);
                //在背景上面绘制图片
                if (x > padding) {//滑动距离大于padding时开始绘制图片
                    //指定图片绘制的位置
                    Rect rect = new Rect();//画图的位置
                    rect.left = itemView.getLeft() + padding;
                    //图片居中
                    rect.top = itemTop + (itemView.getBottom() - itemTop - bitmap.getHeight()) / 2;
                    int maxRight = rect.left + bitmap.getWidth();
                    rect.right = Math.min(x, maxRight);
                    rect.bottom = rect.top + bitmap.getHeight();
                    //指定图片的绘制区域
                    Rect rect1 = null;
                    if (x < maxRight) {
                        //不能再外面初始化，否则dx大于画图区域时，删除图片不显示
                        rect1 = new Rect();
                        rect1.left = 0;
                        rect1.top = 0;
                        rect1.bottom = bitmap.getHeight();
                        rect1.right = x - padding;
                    }
                    c.drawBitmap(bitmap, rect1, rect, paint);
                }
                // 绘制时需调用平移动画，否则滑动看不到反馈
                itemView.setTranslationX(dX);

                float alpha = 1.0f - Math.abs(dX) / (float) itemView.getWidth();
                itemView.setAlpha(alpha);
            } else {
                // 如果在getMovementFlags指定了向左滑动(ItemTouchHelper.START)时,
                // 则绘制工作可参考向右的滑动绘制，也可直接使用下面语句交友系统自己处理
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        } else {
            //拖动时有系统自己完成
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        //重置改变，防止由于复用而导致的显示问题
        viewHolder.itemView.setScrollX(0);
        ((ItemTouchHelperAdapter.ViewHolder)viewHolder).tvDelete.setText("删除");
//        FrameLayout.LayoutParams params =
//                (FrameLayout.LayoutParams) ((ItemTouchHelperAdapter.ViewHolder)viewHolder).ivImg.getLayoutParams();
//        params.width = 150;
//        params.height = 150;
//        ((ItemTouchHelperAdapter.ViewHolder) viewHolder).ivImg.setLayoutParams(params);
//        ((ItemTouchHelperAdapter.ViewHolder) viewHolder).ivImg.setVisibility(View.INVISIBLE);
    }

    /**
     * 获取删除方块的宽度
     */
    public int getSlideLimitation(RecyclerView.ViewHolder viewHolder){
        ViewGroup viewGroup = (ViewGroup) viewHolder.itemView;
        return viewGroup.getChildAt(1).getLayoutParams().width;
    }

    public interface Callback {
        // 在滑动回调方法onSwiped中回调该方法，posion通过ViewHolder.getAdapterPosition()获得
        void onItemDelete(int positon);

        // 在onMove方法中回调该方法，并将拖拽Item 的posion和目标Item的posion传入，
        // posion通过ViewHolder.getAdapterPosition()获得
        void onMove(int fromPosition, int toPosition);
    }

    // 在我们自定义的Adapter中实现RecycleItemTouchHelper.ItemTouchHelperCallback接口
}
