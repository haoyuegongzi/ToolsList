package com.mydemo.toolslist.viewpager2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.mydemo.toolslist.R;
import com.mydemo.toolslist.log.Logs;

import java.util.ArrayList;
import java.util.List;

public class ViewPager2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager2);
        ViewPager2();
    }

    private void ViewPager2(){
        ViewPager2 vp2 = findViewById(R.id.vp2);
        List<Integer> xiaoxiao = new ArrayList();
        xiaoxiao.add(R.mipmap.xiaoxiao01);
        xiaoxiao.add(R.mipmap.xiaoxiao02);
        xiaoxiao.add(R.mipmap.xiaoxiao03);
        xiaoxiao.add(R.mipmap.xiaoxiao04);
        xiaoxiao.add(R.mipmap.xiaoxiao05);
        xiaoxiao.add(R.mipmap.xiaoxiao06);

        PagerAdapter pagerAdapter = new PagerAdapter(this);
        vp2.setAdapter(pagerAdapter);
        pagerAdapter.updateView(xiaoxiao);
        // 水平滑动还是垂直滑动，ViewPager2.ORIENTATION_VERTICAL：垂直滑动；
        // ViewPager2.ORIENTATION_HORIZONTAL：水平滑动。
        vp2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        // smoothScroll参数的作用：当我们指定的默认item不是第一个的时候，界面显示我们制定的item时，
        // 是直接显示还是有一个滑动的过程滑动到制定的item去
//        vp2.setCurrentItem(2, false);




        //页面滑动事件监听
        vp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            //滑动过程中
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                Logs.log("onPageScrolled()：position===" + position +
                            "，positionOffset===" + positionOffset +
                            "，positionOffsetPixels===" + positionOffsetPixels);
            }

            //当一个item划过一半宽高界面的时候会执行，确定是哪一个界面
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Logs.log("onPageSelected()：position===" + position);
            }

            // 滑动的整个过程
            // state=0滑动结束，state=1开始滑动，state=2滑动过程中
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                Logs.log("onPageScrollStateChanged()：state===" + state);
            }
        });
    }
}