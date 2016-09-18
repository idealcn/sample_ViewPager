package com.idealcn.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.idealcn.indicator.BasePagerIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BasePagerIndicator.OnPageChangeListener {


    private ViewPager mMainPager;

    private List<SimpleFragment> fragments = new ArrayList<>();

    private List<String> titles = new ArrayList<>();

    private FragmentPagerAdapter adapter;

    private BasePagerIndicator indicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titles.add("短信");
        titles.add("收藏");
        titles.add("推荐");
        titles.add("精彩");
        titles.add("历史");
        titles.add("展望");
        titles.add("民生");
        titles.add("社会");
        titles.add("军事");
        titles.add("科技");
        titles.add("数码");




        mMainPager = (ViewPager) findViewById(R.id.main_pager);
        indicator = (BasePagerIndicator) findViewById(R.id.indicator);
        indicator.setTabVisibleCount(7);
        indicator.setTabItemTitles(titles);
        initData();

        indicator.setViewPager(mMainPager,0);

        indicator.addOnPageChangeListener(this);

    }

    private void initData() {
        for (String title : titles){
            SimpleFragment fragment = SimpleFragment.newInstance(title);
            fragments.add(fragment);
        }

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return titles.size();
            }
        };

        mMainPager.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
