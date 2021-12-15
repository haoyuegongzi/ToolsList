package com.mydemo.toolslist.touch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.mydemo.toolslist.R;

import java.util.ArrayList;
import java.util.List;

public class ItemTouchHelperActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    final String TAG = "TAGTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_touch_helper);

        mRecyclerView = findViewById(R.id.mRecyclerView);
        initData();
        mItemTouchHelper();
    }

    ItemTouchHelperAdapter itemTouchHelperAdapter;
    private void initData(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("item " + i);
        }

        itemTouchHelperAdapter = new ItemTouchHelperAdapter(this, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(itemTouchHelperAdapter);
    }

    private void mItemTouchHelper() {
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(new ItemTouchHelperCallback.Callback() {
            @Override
            public void onItemDelete(int positon) {
                Log.e(TAG, "onItemDelete: positon=====" + positon);
            }

            @Override
            public void onMove(int fromPosition, int toPosition) {
                Log.e(TAG, "onMove: fromPosition=====" + fromPosition);
                Log.e(TAG, "onMove: toPosition=====" + toPosition);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        //调用ItemTouchHelper的attachToRecyclerView()方法将ItemTouchHelper和RecyclerView关联起来
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }
}