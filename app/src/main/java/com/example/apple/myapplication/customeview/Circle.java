package com.example.apple.myapplication.customeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by fazhao on 16/8/21.
 */
public class Circle extends View{
    Context mContext;
    DisplayMetrics dm;
    public Circle(Context context) {
        super(context);
        mContext = context;
        dm = context.getResources().getDisplayMetrics();
    }

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        dm = context.getResources().getDisplayMetrics();
    }

    public Circle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        dm = context.getResources().getDisplayMetrics();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**
        * 圆盘
        * */
        super.onDraw(canvas);
        Paint paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setAntiAlias(true);
        paintCircle.setStrokeWidth(5);
        canvas.drawCircle(100,100,100,paintCircle);

        /**
        * 画刻度
        * */
        Paint degree = new Paint();
        degree.setStrokeWidth(3);
        for (int i = 0; i < 24; i++) {
            if(i == 0 || i == 6 || i == 12 || i == 18) {
                degree.setStrokeWidth(4);
                degree.setTextSize(20);
                canvas.drawLine(100, 0, 100, 60, degree);
                String de = String.valueOf(i);
                canvas.drawText(de, 100 - degree.measureText(de) / 2, 90, degree);
            }else{
                degree.setStrokeWidth(3);
                degree.setTextSize(15);
                canvas.drawLine(100,0,100,30,degree);
                String de = String.valueOf(i);
                canvas.drawText(de,100 - degree.measureText(de) / 2,60,degree);

        }
            canvas.rotate(15,100,100);
        }
    }
}
