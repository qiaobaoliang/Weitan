package com.example.joe.wei.widgt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import com.example.joe.wei.R;


/**
 * Created by wxx on 2016/10/24.
 */
public class StepCircleView extends View {
    //弧度画笔
    private Paint mArcPaint;
    private int mCenter;
    private int mRadius;
    private RectF mArcRectF;
    private Paint mLinePaint;
    private Paint mTextPaint;
    //扫描度数
    private int scanDegrees = 0;
    private SweepGradient mSweepGradient;
    private int startDegrees = 270;
    private int defaultValue;
    //弧的宽度
    private int circleWidth = 50;

    public StepCircleView(Context context) {
        super(context);
        init();
    }

    public StepCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StepCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mArcPaint = new Paint();
        mArcPaint.setStrokeWidth(circleWidth);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setColor(Color.WHITE);
        mArcPaint.setStyle(Paint.Style.STROKE);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(getResources().getColor(R.color.colorDarkBlue));
        mLinePaint.setStrokeWidth(4);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(30);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        initSize();
        //画底部纯白色的圆
        mArcPaint.setShader(null);
        canvas.drawArc(mArcRectF, 270, 360, false, mArcPaint);

        Log.e("--------", "scanDegreees" + scanDegrees);
        //设置画笔渐变色,画带变色的圆
        mArcPaint.setShader(mSweepGradient);
        canvas.drawArc(mArcRectF, 270, scanDegrees, false, mArcPaint);

        //画线，每隔10度画一条，整个圆共36条线
        for (int i = 0; i < 30; i++) {
            canvas.drawLine(mCenter, mCenter - mRadius + 25, mCenter, mCenter - mRadius - circleWidth / 2, mLinePaint);
            //旋转
            canvas.rotate(12, mCenter, mCenter);
        }

    }


    private void initSize() {
        DisplayMetrics dm =getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;

        //圆心
        mCenter = w_screen / 2;
        //外半径
        mRadius = w_screen / 2 - 100;


        //构建一个矩形（正方形）
        mArcRectF = new RectF(mCenter - mRadius, mCenter - mRadius, mCenter + mRadius, mCenter + mRadius);
        //设置渐变色
        /*
        int[] colors = {0xFFE5BD7D, 0xFFFAAA64,
                0xFFFFFFFF, 0xFF6AE2FD,
                0xFF8CD0E5, 0xFFA3CBCB,
                0xFFBDC7B3, 0xFFD1C299, 0xFFE5BD7D,};*/
        int[] colors = {0xFF1f99b8,0xFF38d688,0xFF6867d1,0xFFe12c58,0xFF063dd0};
        mSweepGradient = new SweepGradient(mCenter, mCenter, colors, null);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        //默认宽高;
        defaultValue = Integer.MAX_VALUE;

        switch (mode) {
            case MeasureSpec.AT_MOST:
                //最大值模式 当控件的layout_Width或layout_height属性指定为wrap_content时
                size = Math.min(defaultValue, size);
                break;
            case MeasureSpec.EXACTLY:
                //精确值模式 当控件的android:layout_width=”100dp”或android:layout_height=”match_parent”时

                break;
            default:
                size = defaultValue;
                break;
        }
        defaultValue = size;
        return size;
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        switch (mode) {
            case MeasureSpec.AT_MOST:
                //最大值模式 当控件的layout_Width或layout_height属性指定为wrap_content时
                //Log.e("CXX", "size" + size + "defaultValue" + defaultValue);
                size = Math.min(defaultValue, size);
                break;
            case MeasureSpec.EXACTLY:
                //精确值模式 当控件的android:layout_width=”100dp”或android:layout_height=”match_parent”时

                break;
            default:
                size = defaultValue;
                break;
        }
        return size;
    }

    public void setScanDegrees(int scanDegrees) {
        this.scanDegrees = scanDegrees;
        invalidate();
    }
}
