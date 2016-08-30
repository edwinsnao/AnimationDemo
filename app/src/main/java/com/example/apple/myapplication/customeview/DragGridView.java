package com.example.apple.myapplication.customeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.GridView;

import com.example.apple.myapplication.R;

/**
 * Created by fazhao on 16/8/30.
 */
public class DragGridView extends GridView {
    Canvas canvas;
    String title;
    Paint paint;
    Rect rect;
    public static int x,y,position;

    public DragGridView(Context context) {
        super(context);
    }

    public DragGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.green));
//        Rect rect = new Rect();
//        Paint paint = new Paint();
//        Stirng
//        canvas.drawText("");
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("Down", "Down");
                break;
            case MotionEvent.ACTION_MOVE:
                x = (int) ev.getX();
                y = (int) ev.getY();
                position = pointToPosition(x, y);
                Log.e("position", String.valueOf(position));
                /**
                * also can
                * */
//                ((DragAdapter)getAdapter()).notifyDataSetInvalidated();
                /**
                * like above
                * */
                ((DragAdapter)getAdapter()).notifyDataSetChanged();
                /**
                * I want to draw the text in canvas instead of textview
                * */
//                paint = new Paint();
//                rect = new Rect();
//                paint.setTextAlign(Paint.Align.LEFT);
//                title = "(" + x + "," + y + ")" + "     position:" + position;
//                paint.getTextBounds(title,0,title.length(),rect);
//                canvas = new Canvas();
//                canvas.drawText(title,0,getMeasuredHeight(),paint);
//                this.draw(canvas);
//                break;
            case MotionEvent.ACTION_UP:
                Log.e("UP", "IUP");
                break;
        }
        return super.onTouchEvent(ev);
    }
}
