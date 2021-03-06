package com.example.apple.myapplication.customeview;

/**
 * Created by fazhao on 16/8/22.
 */

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;

/**
 * 设计空间的设计区域（1.0版本）
 * Updated by harvey on 2016-06-01.
 * Email: harveychan@163.com
 */
public class LocationLayout extends RelativeLayout {
    private float oldRotation = 0;
    private boolean isLayout;
    private ViewDragHelper mDragHelper;

    private enum MODE {
        NONE, DRAG, ZOOM

    }
    int screenW;
    int screenH;
    private View edge;
    private int edgeL;
    private int edgeT;
    private int edgeR;
    private int edgeB;
    private int edgeC;
    public int left = 0;
    public int top = 0;
    public int right = 0;
    public int bottom = 0;
    private float startDis;// 开始距离
    MODE mode;
    int lastX, lastY;
    private float rotation = 0;
    private float afterRotation = 0f;
    float oldScaleX = 1f;
    float oldScaleY = 1f;
//    private rotateListener mRotateListener;
    private Context mContext;

    public LocationLayout(Context context) {
        super(context);
        init(context);
    }

    public LocationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }



    private void init(Context context) {
        this.mContext = context;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        screenH = dm.heightPixels;
        screenW = dm.widthPixels;
        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                Log.e("viewDrager",String.valueOf(child));
                Log.e("testcap","test");
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

//            @Override
//            public void onEdgeTouched(int edgeFlags, int pointerId) {
//                super.onEdgeTouched(edgeFlags, pointerId);
//                Log.e("edgeTouch",String.valueOf(edgeFlags));
//            }


            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
//                super.onViewReleased(releasedChild, xvel, yvel);
                if(releasedChild.getLeft()<0 || releasedChild.getRight()> screenW
                        || releasedChild.getTop() < 0 || releasedChild.getBottom() > screenH) {
                    releasedChild.setLeft(screenW / 2);
                    releasedChild.setTop(screenH / 2);
                    releasedChild.requestLayout();
                    releasedChild.invalidate();
                }
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
//                super.onEdgeDragStarted(edgeFlags, pointerId);
                Log.e("edgeStart",String.valueOf(edgeFlags));
                int captureId = 0;
                for (int i = 0; i < getChildCount(); i++) {
                    mDragHelper.captureChildView(getChildAt(i), pointerId);
                    Log.e("child", String.valueOf(getChildAt(i)));
                }
                Log.e("edge",String.valueOf(getChildAt(pointerId)));
                Log.e("edge",String.valueOf(pointerId));
                edge = getChildAt(captureId);
                edgeL = edge.getLeft();
                edgeT = edge.getTop();
                edgeR = edge.getRight();
                edgeB = edge.getBottom();
                edgeC = (edgeL + edgeR) / 2;
                edge.layout(edgeL - edgeC,edgeT,edgeR -edgeC,edgeB);
            }
        });
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
//        copyList = new ArrayList<>();
    }


    @Override
    public void computeScroll() {
//        super.computeScroll();
        if(mDragHelper.continueSettling(true))
            invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (!this.isEnabled()) {
//            return true;
//        }
//        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:
//                mode = MODE.DRAG;
//                isLayout = false;
//                lastX = (int) event.getRawX();
//                lastY = (int) event.getRawY();
//                break;
//            case MotionEvent.ACTION_POINTER_DOWN:// 当屏幕上还有触点（手指），再有一个手指压下屏幕
//                mode = MODE.ZOOM;
//                oldRotation = rotation(event);
//                startDis = distance(event);
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                if (mode == MODE.DRAG) {
//                    int dx = (int) event.getRawX() - lastX;
//                    int dy = (int) event.getRawY() - lastY;
//                    int left = getLeft() + dx;
//                    int top = getTop() + dy;
//                    int right = getRight() + dx;
//                    int bottom = getBottom() + dy;
//                    layout(left, top, right, bottom);
//                    isLayout = true;
//                    this.left = left;
//                    this.top = top;
//                    this.right = right;
//                    this.bottom = bottom;
//                    Log.e("left",String.valueOf(left));
//                    Log.e("right",String.valueOf(right));
//                    Log.e("top",String.valueOf(top));
//                    Log.e("bottom",String.valueOf(bottom));
////
//                    lastX = (int) event.getRawX();
//                    lastY = (int) event.getRawY();
//                } else if (mode == MODE.ZOOM) {
//                    float endDis = distance(event);// 结束距离
//                    rotation = (rotation(event) - oldRotation);
//                    if (endDis > 10f) {
//                        float scale = endDis / startDis;// 得到缩放倍数
//                        scale(scale);
//                        afterRotation = getRotation() + rotation;
//                        setRotation(afterRotation);
////                        mRotateListener.rotate();
//                    }
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_POINTER_UP:// 有手指离开屏幕,但屏幕还有触点（手指）
//                mode = MODE.NONE;
//                break;
//        }
        mDragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * unKnown
     */
    private int viewId = -1;

    public void setOnViewTouch(int viewId) {
        //clearAllViewsFocusable();
        this.viewId = viewId;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        super.onLayout(changed, left, top, right, bottom);
//        if(isLayout) {
//            isLayout = false;
//            layout(left, top, right, bottom);
//        }
//        Log.e("left1",String.valueOf(left));
//        Log.e("right1",String.valueOf(right));
//        Log.e("top1",String.valueOf(top));
//        Log.e("bottom1", String.valueOf(bottom));
//        Log.e("ischanged", String.valueOf(changed));
    }

    public void scale(float scaleFactor) {
//        if (oldScaleX * scaleFactor>1.6f){
//            return;
//        }
        setScaleX(oldScaleX * scaleFactor);
        setScaleY(oldScaleY * scaleFactor);
        oldScaleX = oldScaleX * scaleFactor;
        oldScaleY = oldScaleY * scaleFactor;
    }

    /**
     * 计算两点之间的距离
     */
    public static float distance(MotionEvent event) {
        try {
            float dx = event.getX(1) - event.getX(0);
            float dy = event.getY(1) - event.getY(0);

//            Log.e("x1-y1",event.getX(1)+"-"+event.getY(1));
//            Log.e("x0-y0",event.getX(0)+"-"+event.getY(0));

            return (float) Math.sqrt(dx * dx + dy * dy);
        } catch (Exception e) {
            Log.e("e", e.toString());
            return 0;
        }
    }

    private float rotation(MotionEvent event) {
        try {
            double delta_x = (event.getX(0) - event.getX(1));
            double delta_y = (event.getY(0) - event.getY(1));
            double radians = Math.atan2(delta_y, delta_x);
            return (float) Math.toDegrees(radians);
        } catch (Exception e) {
            return 0;
        }
    }

//    public interface rotateListener {
//        public void rotate();
//    }
//
//    public void setRotateListener(rotateListener mRotateListener) {
//        this.mRotateListener = mRotateListener;
//    }

    public void setCurrentRotation(float rotation) {
        this.afterRotation = rotation;
    }

    /**
     * 获取当前的旋转参数
     */
    public float getCurrentRotation() {
        return afterRotation;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev);
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }
}
