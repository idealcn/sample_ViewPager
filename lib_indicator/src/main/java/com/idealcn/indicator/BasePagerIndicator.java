package com.idealcn.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by idealgn on 16-9-14.
 */
public class BasePagerIndicator extends LinearLayout {

    public BasePagerIndicator(Context context) {
        this(context,null);
    }

    public BasePagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BasePagerIndicator);
        mTabVisibleCount = typedArray.getInt(R.styleable.BasePagerIndicator_tab_visible_count, DEFAULT_TAB_VISIBLE_COUNT);
        if (mTabVisibleCount<0)
            mTabVisibleCount = DEFAULT_TAB_VISIBLE_COUNT;
        typedArray.recycle();
        init();
    }


    private Paint mPaint;
    private Path mPath;
    private int mTriangleWidth;//三角块的宽度
    private int mTriangleHeight;//三角块的高度
    private int mTranslationX;//滑动的距离
    private int mInitTranslationX;//初始化的偏移距离
    private static final float RADIO_TRIANGLE_WIDTH = 1 / 6f;
    //可见的tab个数
    private int mTabVisibleCount;
    //默认的可见的tab个数
    private static final int DEFAULT_TAB_VISIBLE_COUNT = 5;

    private List<String> titles;

    private static final int TEXT_COLOR_NORMAL = 0xFFFFFF00;


    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mPaint.setPathEffect(new CornerPathEffect(3));


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleWidth = (int) (w / mTabVisibleCount * RADIO_TRIANGLE_WIDTH);
        mInitTranslationX = w / mTabVisibleCount / 2 - mTriangleWidth / 2;

        initTriangle();
    }

    private void initTriangle() {

        mTriangleHeight = mTriangleWidth / 2 + 2;
        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mInitTranslationX + mTranslationX, getHeight());
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount == 0) return;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            LinearLayout.LayoutParams lp = (LayoutParams) child.getLayoutParams();
            lp.weight = 0;
            lp.width = getScreenWidth() / mTabVisibleCount;
            child.setLayoutParams(lp);
        }
        setTabItemClickEvent();

    }

    private int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    private void scroll(int position, float positionOffset) {
        Log.d(TAG, "scroll: "+positionOffset);
        int tabWidth = getWidth() / mTabVisibleCount;
        mTranslationX = (int) (tabWidth * positionOffset + tabWidth * position);

        if (getChildCount() <= mTabVisibleCount) return;
        if (positionOffset > 0 && position >= (mTabVisibleCount - 1)) {
            if (position != 1) {
                scrollTo((int) ((position - mTabVisibleCount + 1) * tabWidth + tabWidth * positionOffset), 0);
            } else {
                this.scrollTo((int) (position * tabWidth + tabWidth * positionOffset), 0);
            }
        }

        invalidate();
    }

    /**
     * 此方法应当在{setTabItemTitles}之前调用
     *
     * @param count
     */
    public void setTabVisibleCount(int count) {
        this.mTabVisibleCount = count;
    }

    public void setTabItemTitles(List<String> titles) {
        if (titles == null || titles.size() == 0) return;
        this.titles = titles;
        this.removeAllViews();
        for (String title : this.titles) {
            addView(generateTextView(title));
        }
        setTabItemClickEvent();
    }

    private View generateTextView(String title) {
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / mTabVisibleCount;
        textView.setLayoutParams(lp);
        textView.setText(title);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView.setTextColor(TEXT_COLOR_NORMAL);
        return textView;
    }


    private ViewPager mViewPager;

    public void setViewPager(ViewPager viewPager, int position) {
        this.mViewPager = viewPager;
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private static final String TAG = "host";

    private void setTabItemClickEvent() {
        Log.d("host", "init: " + getScreenWidth() + "---" + getWidth());
        for (int i = 0; i < getChildCount(); i++) {
            final int j = i;
            View view = getChildAt(j);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewPager.setCurrentItem(j);
//                    BasePagerIndicator.this.scrollTo((j+1/2)*getScreenWidth()/mTabVisibleCount,0);
//                    if (j == 0) {
//                        LinearLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
//                        Log.d(TAG, "onClick: " + lp.leftMargin);
//                        lp.leftMargin = 30;
//                        view.setLayoutParams(lp);
//                    }
                }
            });

        }
    }
}
