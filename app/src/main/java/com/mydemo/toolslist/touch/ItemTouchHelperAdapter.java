package com.mydemo.toolslist.touch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mydemo.toolslist.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author: Nixon
 * @ProjectName: ToolsList
 * @Package: com.mydemo.toolslist.touch
 * @ClassName: ItemTouchHelperAdapter
 * @CreateDate: 2021/3/2 18:23
 * @Description: 本类作用描述：
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
public class ItemTouchHelperAdapter extends RecyclerView.Adapter<ItemTouchHelperAdapter.ViewHolder> {
    List<String> list;
    LayoutInflater inflater;
    ItemTouchHelperCallback.Callback callBack;
    public ItemTouchHelperAdapter(Context context, List<String> list){
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_item_touch_helper, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvItemText.setText(list.get(position));
        holder.tvItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == callBack){
                    return;
                }
                callBack.onItemDelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvItemText;
        public TextView tvDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemText = itemView.findViewById(R.id.tvItemText);
            tvDelete = itemView.findViewById(R.id.tvDelete);
        }
    }

    public void setList(List<String> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void setCallBack(ItemTouchHelperCallback.Callback callBack){
        this.callBack = callBack;
    }
}
