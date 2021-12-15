package com.mydemo.toolslist.viewpager2.blank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mydemo.toolslist.R;
import com.mydemo.toolslist.log.Logs;

import java.util.ArrayList;
import java.util.List;

public class BlankActivity extends AppCompatActivity {
    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] titles = {"1", "2", "3", "4", "5"};
    private TabLayoutMediator tabLayoutMediator = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        initView();
    }

    private void initView() {
        ViewPager2 mViewPager2 = findViewById(R.id.mViewPager2);
        final TabLayout mTabLayout = findViewById(R.id.mTabLayout);

        fragmentList.add(BlankFragment.newInstance("1", "一"));
        fragmentList.add(BlankFragment.newInstance("2", "二"));
        fragmentList.add(BlankFragment.newInstance("3", "三"));
        fragmentList.add(BlankFragment.newInstance("4", "四"));
        fragmentList.add(BlankFragment.newInstance("5", "五"));

        // 水平滑动还是垂直滑动，ViewPager2.ORIENTATION_VERTICAL：垂直滑动；
        // ViewPager2.ORIENTATION_HORIZONTAL：水平滑动。
        mViewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        // smoothScroll参数的作用：当我们指定的默认item不是第一个的时候，界面显示我们制定的item时，
        // 是直接显示还是有一个滑动的过程滑动到制定的item去
        // mViewPager2.setCurrentItem(2, false);
        mViewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return fragmentList.size();
            }
        });

        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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

        //将TabLayout和ViewPager2关联起来
        tabLayoutMediator = new TabLayoutMediator(mTabLayout, mViewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //设置TabLayout的当前选中状态
                tab.setText(titles[position]);
            }
        });
        tabLayoutMediator.attach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tabLayoutMediator.detach();
    }
}