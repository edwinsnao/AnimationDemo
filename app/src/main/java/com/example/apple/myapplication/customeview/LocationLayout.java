package com.example.apple.myapplication.customeview;

/**
 * Created by fazhao on 16/8/22.
 */

import android.content.Context;
import android.util.AttributeSet;
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

    private enum MODE {
        NONE, DRAG, ZOOM

    }

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
    /**
     * 保存设计区域所有的图案
     */
//    private List<DrawAttribute.DesignView> copyList;
    /**
     * 是否有视图被触摸
     */
    public static boolean hasTouchView = false;

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
//        copyList = new ArrayList<>();
    }

    /**
     * 获取设计区域所有的设计图案
     */

//    public List<DrawAttribute.DesignView> getCopyDesignViews() {
//        return copyList;
//    }

    /**
     * 添加指定的设计图案View
     */
//    public void addDesignView(View view, ViewGroup.LayoutParams layoutParams) {
//        this.list.add(view);
////        ViewGroup parent = (ViewGroup) view.getView().getParent();
////        if (parent != null) {
////            parent.removeAllViews();
////        }
//        addView(view.getView(), layoutParams);
//    }

//    public void refreshView() {
//        removeAllViews();
//        Log.e("listSize", String.valueOf(list.size()));
//        for (int i = 0; i < list.size(); i++) {
//            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
//            addView(list.get(i).getView(), layoutParams);
//        }
//    }

//    public void saveDesign(){
//        for (int i = 0; i < copyList.size(); i++) {
//            list.get(i).getView().setVisibility(GONE);
//            copyList.get(i).getView().setVisibility(VISIBLE);
//            copyList.get(i).getView().setX(list.get(i).getView().getX());
//            copyList.get(i).getView().setY(list.get(i).getView().getY());
//        }
//    }

//    public void getXY(){
//        for (int i = 0; i < list.size(); i++) {
//            copyList.get(i).getView().setX(list.get(i).getView().getX());
//            copyList.get(i).getView().setY(list.get(i).getView().getY());
//        }
//    }


    /**
     * 添加指定的设计图案View
     */
//    public void addCopyDesignView(DrawAttribute.DesignView view, ViewGroup.LayoutParams layoutParams) {
//        view.getView().setVisibility(GONE);
//        this.copyList.add(view);
////        ViewGroup parent = (ViewGroup) view.getView().getParent();
////        if (parent != null) {
////            parent.removeAllViews();
////        }
//        addView(view.getView(), layoutParams);
//    }

    /**
     * 删除指定的设计图案View
     */
    public void delectViewIndex(int position) {
        this.removeViewAt(position);
        //allViews.notify();
    }

    /**
     * 删除指定的设计图案View
     */
    public void addViewIndex(View v) {
//        this.list.add(list.get(position));
//        this.addView(list.get(position));
//        this.addDesignView();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.addView(v, layoutParams);
        //allViews.notify();
    }

    /**
     * 设计区域大小是否变化
     * true：*；false：*。
     */
    private boolean sizeChange;

    public void changeSize(boolean change) {
        this.sizeChange = change;
    }

    /**
     * 设置是否要裁剪
     */
    private boolean isOverlying = false;

    public void setOverlying(boolean isOverlying) {
        this.isOverlying = isOverlying;
    }

    /**
     * 对设计区域的所有视图进行排序
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!this.isEnabled()) {
            return true;
        }
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mode = MODE.DRAG;
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                if (LocationLayout.hasTouchView) {
                    return super.onTouchEvent(event);
                }
                LocationLayout.hasTouchView = true;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:// 当屏幕上还有触点（手指），再有一个手指压下屏幕
                mode = MODE.ZOOM;
                oldRotation = rotation(event);
                startDis = distance(event);
                break;

            case MotionEvent.ACTION_MOVE:
                if (mode == MODE.DRAG) {
                    int dx = (int) event.getRawX() - lastX;
                    int dy = (int) event.getRawY() - lastY;
                    int left = getLeft() + dx;
                    int top = getTop() + dy;
                    int right = getRight() + dx;
                    int bottom = getBottom() + dy;
                    layout(left, top, right, bottom);
                    this.left = left;
                    this.top = top;
                    this.right = right;
                    this.bottom = bottom;
//
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                } else if (mode == MODE.ZOOM) {
                    float endDis = distance(event);// 结束距离
                    rotation = (rotation(event) - oldRotation);
                    if (endDis > 10f) {
                        float scale = endDis / startDis;// 得到缩放倍数
                        scale(scale);
                        afterRotation = getRotation() + rotation;
                        setRotation(afterRotation);
//                        mRotateListener.rotate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:// 有手指离开屏幕,但屏幕还有触点（手指）
                mode = MODE.NONE;
                LocationLayout.hasTouchView = false;
                break;
        }
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
        return super.onInterceptTouchEvent(ev);
    }
}
