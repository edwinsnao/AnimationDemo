package com.example.apple.myapplication.customeview;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.widget.ViewDragHelper;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by fazhao on 16/8/22.
 */
public class CustomTextView extends TextView {
    private DisplayMetrics dm;
    private Context context;
    private int screenH;
    private int screenW;
    private int centerX;
    private int centerY;
    private TextPaint mTextPaint;

    private ViewDragHelper mDragHelper;

    public CustomTextView(Context context) {
        super(context);
        init(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private float size;
    private boolean isZoom;
    public int left = 0;
    public int top = 0;
    private float canvasScale = 1;
    private int progress = 50;


    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public float getCanvasScale() {
        return canvasScale;
    }

    public void setCanvasScale(float canvasScale) {
        this.canvasScale = canvasScale;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public int right = 0;
    public int bottom = 0;
    public boolean hasGetLocation = false;

    private float afterRotation = 0f;

    public int getLeftValue() {
        return left;
    }

    public int getTopValue() {
        return top;
    }

    public int getRightValue() {
        return right;
    }

    public int getBottomValue() {
        return bottom;
    }

    public float getOldScaleX() {
        return oldScaleX;
    }

    public float getOldScaleY() {
        return oldScaleY;
    }

    /**
     * 图片视图id操作
     */
    private int viewId = -1;

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    /**
     * 判断是否还获取到焦点
     */
    public boolean hasFocused = false;

    public void hasFocusable(boolean hasFocused) {
        this.hasFocused = hasFocused;
    }

    public boolean getHasFocusable() {
        return hasFocused;
    }

    /**
     * 判断是否可以触摸
     */
    public boolean touchAble = false;

    public boolean isTouchAble() {
        return touchAble;
    }

    public void setTouchAble(boolean touchAble) {
        this.touchAble = touchAble;
    }


    private float startDis;// 开始距离
    private float oldRotation = 0;
    private float rotation = 0;

    /**
     * 模式 NONE：无 DRAG：拖拽. ZOOM:缩放
     */
    private enum MODE {
        NONE, DRAG, ZOOM

    }

    MODE mode;

    float lastX, lastY;
    float startX,startY;

    private void init(Context context) {
        this.context = context;
        dm = context.getResources().getDisplayMetrics();
        screenH = dm.heightPixels;
        screenW = dm.widthPixels;
        mTextPaint = getPaint();
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        mDragHelper = ViewDragHelper.create((ViewGroup) getRootView().getParent(), 1.0f, new ViewDragHelper.Callback() {
//            @Override
//            public boolean tryCaptureView(View child, int pointerId) {
//                Log.e("customeTv",String.valueOf(child));
//                return true;
//            }
//        });
    }

    /**
     * 图片视图被点击的事件监听
     */
//    ImgTouchListener textTouchListener;
//
//    public interface ImgTouchListener {
//        void onImgTouchShowed(boolean showed);
//
//        void getImgViewId(int id);
//    }
//
//    public void setOnImgTouchListener(ImgTouchListener textTouchListener) {
//        this.textTouchListener = textTouchListener;
//    }
    public int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.top) + 2;
    }

    ImgClickListener imgClickListener;

    public interface ImgClickListener {
        void onImgClickShowed(int id, boolean showed);

        void getImgViewId(int id);
    }

    public void setOnImgTouchListener(ImgClickListener imgClickListener) {
        this.imgClickListener = imgClickListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!this.isEnabled()) {
            return true;
        }
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mode = MODE.DRAG;
                isZoom = false;
                lastX = event.getRawX();
                lastY = event.getRawY();
                startX = event.getRawX();
                startY = event.getRawY();
//                if (imgClickListener!=null){
//                    imgClickListener.onImgClickShowed(viewId,true);
//                }
//                if (!touchAble){
//                    setScale3(this,1f,1.5f,100,false);
//                    return false;
//                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN:// 当屏幕上还有触点（手指），再有一个手指压下屏幕
                mode = MODE.ZOOM;
                oldRotation = rotation(event);
                startDis = distance(event);
//                if (imgClickListener!=null){
//                    imgClickListener.onImgClickShowed(viewId,true);
//                }
//
                break;

            case MotionEvent.ACTION_MOVE:
                if (mode == MODE.DRAG) {
                    float dx = (event.getRawX() - lastX) / canvasScale;
                    float dy = (event.getRawY() - lastY) / canvasScale;
                    float left = getLeft() + dx;
                    float top = getTop() + dy;
                    float right = getRight() + dx;
                    float bottom = getBottom() + dy;
//                    if(right - mTextPaint.measureText(getText().toString())<0 || left > screenW - 0|| bottom < 0||top - getFontHeight(getTextSize())> screenH) {
                    if(left < 0 || right > screenW || top < 0 || bottom >screenH) {
                        Log.e("enter", "enter");
                        /**
                        * move to the center of screen
                        * */
//                        centerX = (int) ((screenW) / 2);
//                        centerY = (int) ((screenH) / 2);
//                        left = centerX - (int) mTextPaint.measureText(getText().toString()) / 2;
//                        right = centerX + (int) mTextPaint.measureText(getText().toString()) / 2;
//                        top = centerY - getFontHeight(getTextSize()) / 2;
//                        bottom = centerY + getFontHeight(getTextSize()) / 2;
////                         java.lang.IllegalStateException: Unable to create layer for CustomTextView
//                        layout((int) left, (int) top
//                                , (int) right, (int) bottom);


                        /**
                        * move to the last position
                        * */
                        centerX = (int) Math.ceil(startX);
                        centerY = (int) Math.ceil(startY);
                        left = centerX - (int) mTextPaint.measureText(getText().toString()) / 2;
                        right = centerX + (int) mTextPaint.measureText(getText().toString()) / 2;
                        top = centerY - getFontHeight(getTextSize()) / 2;
                        bottom = centerY + getFontHeight(getTextSize()) / 2;
                        Log.e("cX-xY",centerX+"-"+centerY);
                        Log.e("o-l-t-r-b", left + "-" + top + "-" + right + "-" + bottom);
                        layout((int) left, (int) top
                                , (int) right, (int) bottom);
                        this.left = (int) left;
                        this.top = (int) top;
                        this.right = (int) right;
                        this.bottom = (int) bottom;
                    } else {
                        layout((int) left, (int) top, (int) right, (int) bottom);
                        Log.e("o-l-t-r-b", left + "-" + top + "-" + right + "-" + bottom);
                        this.left = (int) left;
                        this.top = (int) top;
                        this.right = (int) right;
                        this.bottom = (int) bottom;
                    }
//
                    lastX = event.getRawX();
                    lastY = event.getRawY();
//                    if (imgClickListener!=null){
//                        imgClickListener.onImgClickShowed(viewId,true);
//                    }
                } else if (mode == MODE.ZOOM) {
                    isZoom = true;
                    float endDis = distance(event);// 结束距离
                    rotation = (rotation(event) - oldRotation);
                    if (endDis > 10f) {
                        float scale = endDis / startDis;// 得到缩放倍数
                        scale(scale);

                        afterRotation = getRotation() + rotation;
                        setRotation(afterRotation);

                        //setRotation(getRotation()+rotation);
                    }
//                    if (imgClickListener!=null){
//                        imgClickListener.onImgClickShowed(viewId,true);
//                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:// 有手指离开屏幕,但屏幕还有触点（手指）
                mode = MODE.NONE;
                if (imgClickListener != null) {
                    imgClickListener.onImgClickShowed(viewId, isZoom);
                }


                break;
        }
//        mDragHelper.processTouchEvent(event);
        return true;
    }

    float oldScaleX = 1f;
    float oldScaleY = 1f;

    public void scale(float scaleFactor) {
        setScaleX(oldScaleX * scaleFactor);
        setScaleY(oldScaleY * scaleFactor);
        oldScaleX = oldScaleX * scaleFactor;
        oldScaleY = oldScaleY * scaleFactor;
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

    /**
     * 计算两点之间的距离
     */
    public static float distance(MotionEvent event) {
        try {
            float dx = event.getX(1) - event.getX(0);
            float dy = event.getY(1) - event.getY(0);
            return (float) Math.sqrt(dx * dx + dy * dy);
        } catch (Exception e) {
            Log.e("e", e.toString());
            return 0;
        }
    }

    public void setScale3(final View view, float startScale, float endScale, int time, final boolean end) {
        com.nineoldandroids.animation.AnimatorSet set = new com.nineoldandroids.animation.AnimatorSet();
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(startScale, endScale);
        valueAnimator.setDuration(time);

        valueAnimator.setTarget(view);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (Float) animation.getAnimatedValue();
                view.setScaleX(scale);
                view.setScaleY(scale);
            }

        });
        valueAnimator.addListener(new ValueAnimator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (end) {
                    mode = MODE.NONE;
                    if (imgClickListener != null) {
                        imgClickListener.onImgClickShowed(viewId, false);
                    }
                } else {
                    setScale3(view, 1.5f, 1f, 100, true);
                }


            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.play(valueAnimator);
        set.start();
    }

    /**
     * 获取当前的旋转参数
     */
    public float getCurrentRotation() {
        return afterRotation;
    }

    public void setCurrentRotation(float rotation) {
        this.afterRotation = rotation;
    }
}
