package com.idealcn.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.idealcn.indicator.BasePagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {


    private ViewPager mMainPager;

    private List<SimpleFragment> fragments = new ArrayList<>();

    private List<String> titles = Arrays.asList("短信","收藏","推荐");

    private FragmentPagerAdapter adapter;

    private BasePagerIndicator indicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainPager = (ViewPager) findViewById(R.id.main_pager);
        indicator = (BasePagerIndicator) findViewById(R.id.indicator);

        initData();

        mMainPager.addOnPageChangeListener(this);
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
        indicator.scrollTo((int) positionOffset,0);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
