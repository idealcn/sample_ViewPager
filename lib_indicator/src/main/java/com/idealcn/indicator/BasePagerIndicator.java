package com.idealcn.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by idealgn on 16-9-14.
 */
public class BasePagerIndicator extends LinearLayout{

    public BasePagerIndicator(Context context) {
        super(context);
        init();
    }

    public BasePagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BasePagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint mPaint;
    private Path mPath;
    private int mTriangleWidth;//三角块的宽度
    private int mTriangleHeight;//三角块的高度
    private int mTranslationX;//滑动的距离
    private int mInitTranslationX;//初始化的偏移距离
    private static final float RADIO_TRIANGLE_WIDTH = 1/6f;


    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mPaint.setPathEffect(new CornerPathEffect(3));


    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
         mTriangleWidth = (int) (w/3 * RADIO_TRIANGLE_WIDTH);
        mInitTranslationX = w/3/2 - mTriangleWidth /2 ;
        
        initTriangle();
    }

    private void initTriangle() {

        mTriangleHeight = mTriangleWidth/2 + 2;
        mPath = new Path();
        mPath.moveTo(0,0);
        mPath.lineTo(mTriangleWidth,0);
        mPath.lineTo(mTriangleWidth/2,-mTriangleHeight);
        mPath.close();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mInitTranslationX+mTranslationX,getHeight());
        canvas.drawPath(mPath,mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }
}
