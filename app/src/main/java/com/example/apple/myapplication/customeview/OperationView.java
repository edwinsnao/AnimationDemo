package com.example.apple.myapplication.customeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x = this.getX();
        float y = this.getY();
        int w = this.getWidth();
        int h = this.getHeight();
        Log.e("x",String.valueOf(x));
        Log.e("y", String.valueOf(y));
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(context.getResources().getColor(R.color.colorPrimary));
        canvas.drawText("click to scale",x + w / 2,y + h / 2,paint);
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

    public void scale(){
//        Matrix matrix = new Matrix();
//        matrix.postScale(2,2);
        this.getMatrix().setScale(2, 2);
    }
}
