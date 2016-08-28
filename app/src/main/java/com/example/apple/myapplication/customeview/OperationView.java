package com.example.apple.myapplication.customeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.apple.myapplication.MainActivity;
import com.example.apple.myapplication.R;

/**
 * Created by fazhao on 16/8/27.
 */
public class OperationView extends View {
    private Context context;

    public OperationView(Context context) {
        super(context);
        this.context = context;
    }

    public OperationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public OperationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                MainActivity.xy.setText("" + event.getRawX() + "," + event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                MainActivity.xy.setText("" + event.getRawX() + "," + event.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                MainActivity.xy.setText("" + event.getRawX() + "," + event.getRawY());
                break;
        }
        return true;
    }

    /**
     * 获取字体高度
     *
     * @param textSize : textSize==null时  获取字符串的默认字体,
     */
    public static int getFontHeight(Integer textSize) {
        Paint paint = new Paint();
        if (textSize != null) {
            paint.setTextSize(textSize);
        }
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) (fm.descent - fm.ascent);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * the canvas drawColor before onDraw will not cover the text!(not cover now)
         * */
        canvas.drawColor(getResources().getColor(R.color.colorPrimary));
        super.onDraw(canvas);
        float x = this.getX();
        float y = this.getY();
        int w = this.getWidth();
        int h = this.getHeight();
        Log.e("x", String.valueOf(x));
        Log.e("y", String.valueOf(y));
        Log.e("x+w/2", String.valueOf(x + w / 2));
        Log.e("y+h/2", String.valueOf(y + h / 2));
        Paint paint = new Paint();
        Rect rect = new Rect();
        paint.setTextAlign(Paint.Align.LEFT);
//        paint.setColor(getResources().getColor(R.color.white));
        paint.getTextBounds("click to scale", 0 , "click to scale".length(), rect);
        paint.setAntiAlias(true);
//        paint.setColor(getResources().getColor(R.color.colorPrimary));
        /**
         * display1: can display is center(correct)
         * */
//        canvas.drawText("click to scale", 0, rect.width(), paint);
        /**、
         * this is wrong not display
         * */
//        canvas.drawText("click to scale",x+w/2,y+h/2,paint);

        /**
        * the canvas drawColor after onDraw will cover the text!
        * */
//        canvas.drawColor(getResources().getColor(R.color.colorPrimary));
        /**
         * default color is black
         * display2: can display but not in center
         * */
//        canvas.drawText("click to scale",0,getFontHeight(null),paint);
        /**
         * default color is black
         * display2: can display in center(not strict)
         * */
//        canvas.drawText("click to scale",(getMeasuredWidth() - rect.width()) / 2,(getMeasuredHeight() + rect.height()) / 2,paint);

        /**
         * default color is black
         * display2: can display in center correctly
         * */
//        Paint.FontMetricsInt metricsInt = paint.getFontMetricsInt();
//        canvas.drawText("click to scale",(getMeasuredWidth() - rect.width()) / 2,(getMeasuredHeight() - metricsInt.bottom - metricsInt.top) / 2,paint);

        /**
         * default color is black
         * display2: can display in center correctly
         * the same as above?
         * */
        Paint.FontMetrics metricsInt = paint.getFontMetrics();
        canvas.drawText("click to scale",(getMeasuredWidth() - rect.width()) / 2,(getMeasuredHeight() - metricsInt.descent - metricsInt.ascent) / 2,paint);
//        Rect rect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void scale() {
//        Matrix matrix = new Matrix();
//        matrix.postScale(2,2);
        this.getMatrix().setScale(2, 2);
    }
}
