package com.example.apple.myapplication.customeview;

/**
 * Created by fazhao on 2016/10/8.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.myapplication.utils.AnimationUtils;


/**
 * Created by Kings on 2016/7/20.
 */
public class MyOnTextView extends TextView {
    private long start;
    private int i;
    private long end;
    private long time;
    private float downX = 0;
    private float downY = 0;
    private float MoveX = 0;
    private float MoveY = 0;
    private float DragX = 0;
    private float previousY = 0;
    private float previousX = 0;
    private float DragY = 0;
    private Context context;

    public MyOnTextView(Context context) {
        super(context);
        init(context);
    }

    public MyOnTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyOnTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        Log.e("onclick1", "onclick");
        this.setText("click" + ++i);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("OnTextdispatch",""+event.getAction());
//        boolean intercept = false;
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                intercept = false;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                /**
//                * 这样相当于拦截了不处理onTouchEvent的
//                * */
////                intercept = true;
////                break;
//                return super.dispatchTouchEvent(event);
//            case MotionEvent.ACTION_UP:
//                intercept = false;
//                break;
//        }
//        return intercept;
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        downX = this.getX();
        downY = this.getY();
        Log.e("OnTextTouchEvent",""+motionEvent.getAction());
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = motionEvent.getX();
                downY = motionEvent.getY();
                start = System.currentTimeMillis();
                Log.e("motiondown", "motiondown");
                break;
            case MotionEvent.ACTION_MOVE:
                MoveX = motionEvent.getX();
                MoveY = motionEvent.getY();
                DragX = MoveX - downX;
                DragY = MoveY - downY;
//                Log.e("move", "move");
                this.layout((int) (this.getLeft() + DragX), (int) this.getTop(), (int)
                        (this.getLeft() + DragX + this.getWidth()), (int)
                        (this.getTop() + DragY + this.getHeight()));
                previousX = DragX;
                previousY = DragY;
                return true;
            case MotionEvent.ACTION_UP:
                end = System.currentTimeMillis();
                time = end - start;
                MoveX = motionEvent.getX();
                MoveY = motionEvent.getY();
                Log.e("time", String.valueOf(time));
                Log.e("move", String.valueOf(Math.abs(MoveX - downX)));
                Log.e("move1", String.valueOf(DragX));
                if (Math.abs(DragX) < 1 || Math.abs(DragY) < 1) {
                    Log.e("onclick", "onclick");
//                    this.setText("click" + ++i);
                    AnimationUtils.setClicked1(this);
                    if (time >= 500 && time <= 1000) {
                        Toast.makeText(context, "short", Toast.LENGTH_SHORT).show();
                        this.setText("short");
                    } else if (time >= 1000) {
                        Toast.makeText(context, "long", Toast.LENGTH_SHORT).show();
                        this.setText("long");
                    }
                }
//                this.setText("up");

                Log.e("up", "up");
                break;
        }
        Log.e("what", "what");
        return true;
    }
}

