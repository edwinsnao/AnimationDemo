package com.example.apple.myapplication.customeview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by fazhao on 2016/10/8.
 */

/**
* 重叠的布局,上面的不响应事件并传递给下面的
* */
public class OnLayout extends LinearLayout {
    public OnLayout(Context context) {
        super(context);
    }

    public OnLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OnLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return super.dispatchTouchEvent(ev);
        return false;
    }
}
