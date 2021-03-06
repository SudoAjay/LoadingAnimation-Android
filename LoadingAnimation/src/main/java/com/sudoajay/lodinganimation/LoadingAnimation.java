package com.sudoajay.lodinganimation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;


public class LoadingAnimation extends View {


    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Context mContext;
    private Paint mPaint;
    private int anglePoint = 0, alpha = 700, reduceAlpha = 18, mainRadius = 80, otherRadius = 20,
            reduceOtherRadius = 1, color = Color.BLACK;
    private final int gap = 12;
    private boolean stop=true, start;


    public LoadingAnimation(final Context mContext, final AttributeSet attrs) {
        super(mContext, attrs);
        init(mContext, attrs);
        this.mContext = mContext;

        setUp();

    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingAnimation, 0, 0);
        //Reading values from the XML layout
        try {
            int n = typedArray.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = typedArray.getIndex(i);
                if (attr == R.styleable.LoadingAnimation_la_set_color) {
                    color = typedArray.getColor(attr, Color.BLACK);

                    //note that you are accessing standart attributes using your attrs identifier
                } else if (attr == R.styleable.LoadingAnimation_la_set_alpha) {
                    alpha = typedArray.getInt(attr, 700);

                } else if (attr == R.styleable.LoadingAnimation_la_set_reduce_alpha) {
                    reduceAlpha = typedArray.getInt(attr, 18);

                } else if (attr == R.styleable.LoadingAnimation_la_set_angle_point) {
                    anglePoint = typedArray.getInt(attr, 0);

                } else if (attr == R.styleable.LoadingAnimation_la_set_main_radius) {
                    mainRadius = typedArray.getInt(attr, 80);

                } else if (attr == R.styleable.LoadingAnimation_la_set_other_radius) {
                    otherRadius = typedArray.getInt(attr, 20);

                } else if (attr == R.styleable.LoadingAnimation_la_set_reduce_other_radius) {
                    reduceOtherRadius = typedArray.getInt(attr, 1);

                }

            }
        } finally {
            typedArray.recycle();
        }
    }
        // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

    }

    // override onDraw
    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        int alpha = this.alpha, rad = this.otherRadius, reduceAlpha = this.reduceAlpha;
        // draw the mPath with the mPaint on the canvas when onDraw
        canvas.drawPath(mPath, mPaint);


        if (!stop) {
            for (int i = 0; i < 10; i++) {
                mPaint.setAlpha(alpha);
                canvas.drawCircle((float) getWidth() / 2 + mainRadius * (float) Math.cos(anglePoint),
                        (float) getHeight() / 2 + mainRadius * (float) Math.sin(anglePoint), rad, mPaint);
                alpha -= reduceAlpha;
                rad -= reduceOtherRadius;
                anglePoint += gap;
            }

            this.postInvalidateDelayed(100);
        }
    }

    // handler Thread
    public void setTiming(final int delayMillis) {
        stop = false;
        this.postInvalidateDelayed(100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                clearCanvas();
            }
        }, delayMillis);

    }


    public void stop() {
        clearCanvas();
    }


    @SuppressLint("WrongCall")
    public void start() {
        start= true;
        setUp();
        if (start) {
            stop = false;
            this.postInvalidateDelayed(100);
        }
    }

    // clear The Whole Path
    public void clearCanvas() {
        stop = true;
        mPath.reset();
        invalidate();
    }

    private void setUp(){
        // we set a new Path
        mPath = new Path();

        // and we set a new Paint with the desired attributes
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mWidth = MeasureSpec.getSize(widthMeasureSpec);
        int mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    public int getAnglePoint() {
        return anglePoint;
    }

    public void setAnglePoint(int anglePoint) {
        this.anglePoint = anglePoint;
    }


    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
        mPaint.setAlpha(alpha);

        if(stop) invalidate();
    }

    public int getReduceAlpha() {
        return reduceAlpha;
    }

    public void setReduceAlpha(int reduceAlpha) {
        this.reduceAlpha = reduceAlpha;
    }

    public int getMainRadius() {
        return mainRadius;
    }

    public void setMainRadius(int mainRadius) {
        this.mainRadius = mainRadius;
    }

    public int getOtherRadius() {
        return otherRadius;
    }

    public void setOtherRadius(int otherRadius) {
        this.otherRadius = otherRadius;
    }

    public int getReduceOtherRadius() {
        return reduceOtherRadius;
    }

    public void setReduceOtherRadius(int reduceOtherRadius) {
        this.reduceOtherRadius = reduceOtherRadius;
    }

    public int getGap() {
        return gap;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        mPaint.setColor(color);

        if(stop)invalidate();

    }

}