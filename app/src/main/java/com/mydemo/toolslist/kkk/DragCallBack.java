package com.mydemo.toolslist.kkk;

import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 作者：Created by chen1 on 2020/4/12.
 * 邮箱：chen126jie@163.com
 * TODO：
 */
public class DragCallBack<T> extends ItemTouchHelper.Callback {
    RecyclerView rv;
    RecyclerView.ViewHolder lastDragViewHolder;
    //先勉强用这个占个位置
    RecyclerView.Adapter adapter;

    List<T> mData;

    public DragCallBack(RecyclerView mRecyclerView, RecyclerView.Adapter mAdapter, List<T> data){
        rv = mRecyclerView;
        adapter = mAdapter;
        mData = data;
    }

    /**
     * 当用户长按后，会触发拖拽的选中效果，viewHolder就是当前的选中
     * @param viewHolder
     * @param actionState 取下面中的值
     *                    {@link ItemTouchHelper#ACTION_STATE_IDLE},
     *                    {@link ItemTouchHelper#ACTION_STATE_SWIPE},
     *                    {@link ItemTouchHelper#ACTION_STATE_DRAG}.
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        //如果状态为拖拽，说明选中了
        //我在xml里面写的scale都为0.8 我们需要把当前的视图放大一下，所以设置为1就可以了
        if (viewHolder != null && actionState ==  ItemTouchHelper.ACTION_STATE_DRAG){
            lastDragViewHolder = viewHolder;
            viewHolder.itemView.setScaleX(1);
            viewHolder.itemView.setScaleY(1);
        }

        //ACTION_STATE_IDLE就是松开了，把大小改为原状
        if (lastDragViewHolder != null && actionState == ItemTouchHelper.ACTION_STATE_IDLE){
            lastDragViewHolder.itemView.setScaleX(0.8F);
            lastDragViewHolder.itemView.setScaleY(0.8F);
            lastDragViewHolder = null;
            //是为了实现RecyclerView自动回位的，实现在后面给出
            ensurePositionV1(rv);
        }
    }

    /**
     * 我们不需要滑动删除，所以返回false
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    /**
     * 这个是用来设置用户可以对 viewHolder进行什么操作，推荐用makeMovementFlags(int dragFlags, int swipeFlags)来处理
     * 例如 makeMovementFlags(UP | DOWN, LEFT);就是可以上下拖拽，向左滑动
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //拖拽
        int dragFlag = makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
        //左右滑动
        int swipeFlag = makeMovementFlags(ItemTouchHelper.ACTION_STATE_SWIPE,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
        //这里：对item的操作：ItemTouchHelper.ACTION_STATE_DRAG：表示拖拽，
        //                   ItemTouchHelper.ACTION_STATE_SWIPE：表示左右滑动
        //                   ItemTouchHelper.ACTION_STATE_IDLE：表示不响应对item的滑动操作
        //                   makeMovementFlags(dragFlag, swipeFlag)：也可以表示不响应对item的滑动操作，但麻烦许多。
        return makeMovementFlags(dragFlag, swipeFlag);
    }

    /**
     * 这个是拖拽的回调
     * @param recyclerView
     * @param viewHolder  这个是我们拖拽中的ViewHolder
     * @param target      这个是离我们拖拽ViewHolder最近的ViewHolder，也就是我们松手后需要替换的ViewHolder
     * @return  返回true的话 我们已经做好相关操作了，false就我们没做啥操作
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //要拖拽的位置
        int fromPosition = viewHolder.getAdapterPosition();
        //要放置的目标位置
        int toPosition = target.getAdapterPosition();
        //这里的bannerData只是暂时性的代替，实际使用中，列表RecyclerView用的是什么数据这里就传什么数据
        Collections.swap(mData, fromPosition, toPosition);
        adapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //这个是滑动的回调，这次关注拖拽
        int position = viewHolder.getAdapterPosition();
        mData.remove(position);
        adapter.notifyItemRemoved(position);
    }

    /**
     * 用来判断 target是否可以被替换
     * @param recyclerView
     * @param current
     * @param target
     * @return  true :target可以被current替换
     *          false：不可以
     */
    @Override
    public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current, RecyclerView.ViewHolder target) {
        //汽车之家最后一个为加号，所以不支持拖拽
        if (target.getAdapterPosition() == recyclerView.getAdapter().getItemCount() -1){
            return false;
        }
        return super.canDropOver(recyclerView, current, target);
    }

    /**
     * 确保第一个item自动回位，第一版
     * @param recyclerView
     */
    public void ensurePositionV1(RecyclerView recyclerView){
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstVisible = layoutManager.findFirstVisibleItemPosition();
        View firstVisibleView = layoutManager.findViewByPosition(firstVisible);
        int left  = firstVisibleView.getLeft();
        int width = firstVisibleView.getWidth();
        //left就是这个item被RecyclerView裁剪掉了多少
        //如果第一个可视item的左边界在RecyclerView内左边线的右边则 left是正值，
        //如果在在RecyclerView内左边线的左边则为负值
        //如果滑动超过一定距离，就滚动到下个item去，我这里取一半多一点点，
        if (Math.abs(left) > width * 0.6){
            recyclerView.getScrollY();
            layoutManager.scrollToPositionWithOffset(firstVisible+1,0);
        }else {
            layoutManager.scrollToPositionWithOffset(firstVisible,0);
        }
    }
}
