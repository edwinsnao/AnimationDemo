package com.example.apple.myapplication.customeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fazhao on 16/8/21.
 */
public class DrawTest extends View {
    public DrawTest(Context context) {
        super(context);
    }

    public DrawTest(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        canvas.drawColor(Color.GREEN);
        canvas.drawPoint(0, 0, paint);
        canvas.drawLine(0, 0, 0, 100, paint);
        float[] lines = {
                0,10,100,10,
                0,20,100,20,
                0,30,100,30
        };
        canvas.drawLines(lines, paint);
        canvas.drawRect(0, 50, 50, 100, paint);
//        canvas.drawRoundRect(100, 50, 150, 100, 10, 10, paint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(150,150,25,paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(150,150,24,paint);
    }
}
