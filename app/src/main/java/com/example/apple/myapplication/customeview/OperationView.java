package com.example.apple.myapplication.customeview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.apple.myapplication.MainActivity;
import com.example.apple.myapplication.R;

/**
 * Created by fazhao on 16/8/27.
 */
public class OperationView extends View {
    private long start,end;
    private Bitmap bitmap,lastBitmap;
    private float oldX = 1,oldY = 1;
    private Context context;
    public static int w,h;
    private InflateEnd inflateEnd;

    public interface InflateEnd{
        public void inflate();
    }

    public void setInflateEnd(InflateEnd inflateEnd){
        this.inflateEnd = inflateEnd;
    }


    public OperationView(Context context) {
        super(context);
        init(context);
    }

    public OperationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OperationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        this.context = context;
//        this.setScaleType(ScaleType.MATRIX);
        this.setDrawingCacheEnabled(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                start = System.currentTimeMillis();
                MainActivity.xy.setText("" + event.getRawX() + "," + event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                MainActivity.xy.setText("" + event.getRawX() + "," + event.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                end = System.currentTimeMillis();
                if(end - start <= 500)
                    this.scale();
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
//        canvas.drawText("click to scale",(getMeasuredWidth() - rect.width()) / 2,(getMeasuredHeight() - metricsInt.descent - metricsInt.ascent) / 2,paint);
        /**
        * because param y is not the text show in center,but the baseline ,so we must set the baseline instead of text to display in center
        * */
        canvas.drawText("click to scale", (getMeasuredWidth() - rect.width()) / 2, (getMeasuredHeight() - (metricsInt.descent + metricsInt.ascent)) / 2, paint);
//        bitmap = getDrawingCache();
//        this.setImageBitmap(bitmap);
//        Rect rect = new Rect();
//        buildDrawingCache();
//        lastBitmap = getDrawingCache();
//        this.setImageBitmap(lastBitmap);
        Log.e("draw","test");
        w = getMeasuredWidth();
        h = getMeasuredHeight();
        Log.e("w-h:",new StringBuilder().append(w).append(h).toString());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("measure","test");
//        w = getMeasuredWidth();
//        h = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("layut","test");
//        w = right - left;
//        h = bottom - top;
    }

    public void scale() {
        /*Matrix matrix = new Matrix();
        matrix.setScale(2, 2);
//        Log.e("matrix",String.valueOf(this.getMatrix()));
//        matrix.setScale(2,2);
        buildDrawingCache();
//        lastBitmap = getDrawingCache();
        lastBitmap = Bitmap.createBitmap(this.getDrawingCache());
        Log.e("lastW",String.valueOf(lastBitmap.getWidth()));
        Log.e("lastH", String.valueOf(lastBitmap.getHeight()));
        bitmap = Bitmap.createBitmap(lastBitmap, 0, 0, lastBitmap.getWidth(), lastBitmap.getHeight(), matrix, true);
        if(lastBitmap!=null && !lastBitmap.isRecycled()){
//            lastBitmap.recycle();
            lastBitmap = null;
        }
//        this.setImageBitmap(bitmap);
        Canvas canvas = new Canvas(bitmap);
        this.draw(canvas);
//        invalidate();*/

        oldX = oldX * 2;
        oldY = oldY * 2;
        this.setScaleX(oldX);
        this.setScaleY(oldY);

// 、       this.getMatrix().setScale(2, 2);、
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e("inflateEnd","test");
        if(inflateEnd != null)
            inflateEnd.inflate();
    }
}
