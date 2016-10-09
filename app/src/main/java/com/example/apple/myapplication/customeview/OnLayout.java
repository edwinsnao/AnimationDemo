package com.example.apple.myapplication.customeview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by fazhao on 2016/10/8.
 */

/**
* 重叠的布局,上面的不响应事件并传递给下面的
* */
public class OnLayout extends LinearLayout {
    private int i = 0;
    private int j = 0;
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
        Log.e("OnLayoutTouch",""+event.getAction());
        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                return false;
//            case MotionEvent.ACTION_MOVE:
//                return true;
        }
        Log.e("ontouch","ontouch");
//        return super.onTouchEvent(event);
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                Log.e("onintercept","ondown");
                break;
            case MotionEvent.ACTION_MOVE:
                intercept = true;
                Log.e("onintercept","onmove");
//                return true;
                return super.onInterceptTouchEvent(ev);
            case MotionEvent.ACTION_UP:
                intercept = false;
                Log.e("onintercept","onup");
                break;
        }
        Log.e("OnLayoutIntercept",""+ev.getAction());
        Log.e("intercept:",i++ +" "+intercept);
        return intercept;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean isMove = false;
        Log.e("OnLayoutdispatch",""+ev.getAction());
//        if(this.onInterceptTouchEvent(ev)){
//            return super.dispatchTouchEvent(ev);
////            return true;
//        }else {
////            return super.dispatchTouchEvent(ev);
////            if(this.getChildCount() != 0)
////            return this.getChildAt(0).dispatchTouchEvent(ev);
////            else
//                return false;
//        }
//        j++;
//        if(j == 2 && MotionEvent.ACTION_MOVE == ev.getAction()) {
//            Log.e("dispatch:","true");
//            j = 0;
//            return super.dispatchTouchEvent(ev);
//        }else
//        return  false;
//        Log.e("dispatch:",ev.getAction()+"");
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                isMove = false;
//                break;
////                return false;
//            case MotionEvent.ACTION_MOVE:
//                Log.e("dispatch:","true");
//                isMove = true;
//                super.dispatchTouchEvent(ev);
//                return true;
//        }
//        return super.dispatchTouchEvent(ev);
//        Log.e("dispatch:","false");
        return false;
//        return false;
    }
}
