package com.example.apple.myapplication.utils;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;


import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * 动画工具类（1.0版本）
 * Updated by harvey on 2016-06-01.
 * Email: harveychan@163.com
 */
public class AnimationUtils {
    private static List<Animator> startAni = new ArrayList<>();
    private static List<Animator> startAniFirst = new ArrayList<>();
    private static List<Animator> endAni = new ArrayList<>();
    private static List<Animator> endAni1 = new ArrayList<>();
    private static List<Animator> anims = new ArrayList<>();

    /**
     * 设置控件的透明度
     *
     * @param view
     * @param startAlpha
     * @param endAlpha
     * @param time
     */
    public static void setAlpha(View view, float startAlpha, float endAlpha, int time) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "alpha", startAlpha, endAlpha);
        anim.setDuration(time);
        anim.start();
    }

    public static void FirstOutSecondIn(final View startView, final View endView, final float scaleX, final float scaleY, final boolean isBig, final Context context, final ImageView bg) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        final int[]times = {0};
        boolean added = false;
        GradientDrawable shape = (GradientDrawable) bg.getDrawable();
        AnimatorSet set = new AnimatorSet();
        final AnimatorSet[] set1 = {new AnimatorSet()};
        final AnimatorSet[] set2 = {new AnimatorSet()};
        final AnimatorSet[] set3 = {new AnimatorSet()};
        final AnimatorSet[] set4 = {new AnimatorSet()};
        final AnimatorSet[] set5 = {new AnimatorSet()};
        final ObjectAnimator animScaleAX1 = ObjectAnimator.ofFloat(bg, "scaleX", 1, 1);
        final ObjectAnimator animScaleAY1 = ObjectAnimator.ofFloat(bg, "scaleY", 1, 1);
        final ObjectAnimator animScaleAX = ObjectAnimator.ofFloat(bg, "scaleX", 1, scaleX);
        final ObjectAnimator animScaleAY = ObjectAnimator.ofFloat(bg, "scaleY", 1, scaleY);
        animScaleAX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
            }
        });
        set5[0].playTogether(animScaleAX1, animScaleAY1, ObjectAnimator.ofFloat(shape, "cornerRadius", 10 * dm.density, 30 * dm.density).setDuration(100));
        set5[0].setDuration(100);
        animScaleAX.setDuration(600);
        animScaleAY.setDuration(600);
        final ObjectAnimator animScaleBX = ObjectAnimator.ofFloat(startView, "scaleX", 1, 1);
        final ObjectAnimator animScaleBY = ObjectAnimator.ofFloat(startView, "scaleY", 1, 1);
        animScaleBX.setDuration(300);
        animScaleBY.setDuration(300);
        set3[0].playTogether(animScaleBX, animScaleBY);
        set3[0].setDuration(300);
//        View layout = null;
//        final ObjectAnimator anim3 = ObjectAnimator.ofFloat(endView, "alpha", 0f, 1f);
//        int count = ((ViewGroup) startView).getChildCount();
        int count = ((ViewGroup) ((ViewGroup) startView).getChildAt(1)).getChildCount();
        View layout = ((ViewGroup) startView).getChildAt(1);
        ObjectAnimator obj;
        ObjectAnimator obj1;
        for (int i = 0; i < count; i++) {
            obj = ObjectAnimator.ofFloat(((ViewGroup) layout).getChildAt(i), "translationY", 0, -6);
            obj.setDuration(100);
            if (i == 2 || i == 3)
                obj.setStartDelay(100);
            startAni.add(obj);
            obj = ObjectAnimator.ofFloat(((ViewGroup) layout).getChildAt(i), "translationY", -6, 72);
            if (i == 2 || i == 3) {
                obj.setDuration(300);
                obj.setStartDelay(200);
                if (!added) {
                    obj.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            times[0]++;
                            if (times[0] == 10)
                                set4[0].start();
                        }
                    });
                    added = true;
                }
            } else {
                obj.setDuration(400);
                obj.setStartDelay(100);
            }
            startAni.add(obj);
            obj = ObjectAnimator.ofFloat(((ViewGroup) layout).getChildAt(i), "alpha", 1, 0);
            obj.setDuration(500);
            startAni.add(obj);
//            obj = ObjectAnimator.ofFloat(((ViewGroup) layout).getChildAt(i), "scaleX", 1, 1);
//            obj.setDuration(500);
//            startAni.add(obj);
//            obj = ObjectAnimator.ofFloat(((ViewGroup) layout).getChildAt(i), "scaleY", 1, 1);
//            obj.setDuration(500);
//            startAni.add(obj);
        }
        set1[0].playTogether(startAni);
        set1[0].setDuration(500);
        set1[0].setStartDelay(100);
        set1[0].addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                startView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                startAni.clear();
//                set4.start();
                startView.setLayerType(View.LAYER_TYPE_NONE,null);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set1[0].start();
        count = ((ViewGroup) startView).getChildCount();
        for (int i = 0; i < count; i++) {
//            startAniFirst.add(ObjectAnimator.ofFloat(((ViewGroup) startView).getChildAt(i), "scaleY", 1, 1).setDuration(600));
//            startAniFirst.add(ObjectAnimator.ofFloat(((ViewGroup) startView).getChildAt(i), "scaleX", 1, 1).setDuration(600));
        }
        startAniFirst.add(ObjectAnimator.ofFloat(shape,"cornerRadius",30 * dm.density,10 * dm.density).setDuration(600));
        if(isBig)
            startAniFirst.add(ObjectAnimator.ofFloat(bg, "translationY", 0, -42).setDuration(600));
        else
            startAniFirst.add(ObjectAnimator.ofFloat(bg, "translationY", 0, -25).setDuration(600));
        startAniFirst.add(animScaleAX);
        startAniFirst.add(animScaleAY);
//        set.playTogether(startAniFirst);
        count = ((ViewGroup) endView).getChildCount();
        /**
         * back
         * */
//        View endViewLayout = ((ViewGroup) endView).getChildAt(1);
        View endLayout = ((ViewGroup) endView).getChildAt(1);
        for (int i = 0; i < ((ViewGroup) endLayout).getChildCount(); i++) {
            ((ViewGroup) endLayout).getChildAt(i).setVisibility(View.VISIBLE);
            obj = ObjectAnimator.ofFloat(((ViewGroup) endLayout).getChildAt(i), "translationY", 66, 0);
            obj.setStartDelay(33 * i);
            obj.setDuration(490);
            endAni.add(obj);
            obj = ObjectAnimator.ofFloat(((ViewGroup) endLayout).getChildAt(i), "alpha", 0, 1);
            obj.setStartDelay(33 * i);
            obj.setDuration(490);
            endAni.add(obj);
//            obj = ObjectAnimator.ofFloat(((ViewGroup) endLayout).getChildAt(i), "scaleX", 1, 1);
//            obj.setDuration(490);
//            obj.setStartDelay(33 * i);
//            endAni.add(obj);
//            obj = ObjectAnimator.ofFloat(((ViewGroup) endLayout).getChildAt(i), "scaleY", 1, 1);
//            obj.setDuration(490);
//            obj.setStartDelay(33 * i);
//            endAni.add(obj);
        }
        endAni.add(ObjectAnimator.ofFloat(bg, "translationY", 0, 0).setDuration(2000));
        set2[0].playTogether(endAni);
        set2[0].setDuration(490 + ((ViewGroup) endLayout).getChildCount() * 33);
        Log.e("count", String.valueOf(((ViewGroup) endLayout).getChildCount()));
        for (int i = 0; i < ((ViewGroup) endLayout).getChildCount(); i++) {
//                    animll.getChildAt(i).setVisibility(View.GONE);
            obj = ObjectAnimator.ofFloat(((ViewGroup) endLayout).getChildAt(i), "translationY", 0, 66);
            obj.setDuration(10);
            endAni1.add(obj);
//                    obj[0] = ObjectAnimator.ofFloat(animll.getChildAt(i),"translationY",0,66);
        }
        set4[0].playTogether(endAni1);
        set4[0].setDuration(10);
        set4[0].addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                set2[0].start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

//        anim3.setDuration(300);
        set2[0].addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Log.e("set2start", "test");
                startView.clearAnimation();
                startView.setVisibility(View.GONE);
                endView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//                startView.setVisibility(View.GONE);
                endView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.e("set2end", "test");
                startAni.clear();
                endAni.clear();
                endAni1.clear();
                bg.clearAnimation();
//                startView.clearAnimation();
                endView.clearAnimation();
                endView.setLayerType(View.LAYER_TYPE_NONE, null);
                set1[0].end();
                set3[0].end();
                set5[0].end();
                set4[0].end();
                set1[0] = null;
                set2[0] = null;
                set3[0] = null;
                set5[0] = null;
                set4[0] = null;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
//        set1.playTogether(startAni);
//        set1.setDuration(300);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                startAni.clear();
//                set2.start();
//                    bg.setImageDrawable(context.getApplicationContext().getResources().getDrawable(R.drawable.menu_background));
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set1[0].setDuration(400);
        set1[0].setStartDelay(100);
        set1[0].start();
        set.playTogether(startAniFirst);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                set3[0].start();
                set5[0].start();
                startAniFirst.clear();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.setDuration(600);
        set.start();
    }

    public static void FirstInSecondOut(final View startView, final View endView, final float scaleX, final float scaleY, final boolean isBig, final Context context, final ImageView bg) {
        final int[] times = {0};
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        boolean added = false;
        GradientDrawable shape = (GradientDrawable) bg.getDrawable();
        View startLayout = ((ViewGroup) startView).getChildAt(1);
        AnimatorSet set = new AnimatorSet();
        final AnimatorSet[] set1 = {new AnimatorSet()};
        final AnimatorSet[] set2 = {new AnimatorSet()};
        final AnimatorSet[] set3 = {new AnimatorSet()};
        final AnimatorSet[] set4 = {new AnimatorSet()};
        final AnimatorSet[] set5 = {new AnimatorSet()};
        final ObjectAnimator animScaleAX = ObjectAnimator.ofFloat(bg, "scaleX", 1, scaleX);
        final ObjectAnimator animScaleAY = ObjectAnimator.ofFloat(bg, "scaleY", 1, scaleY);
        final ObjectAnimator animScaleAX1 = ObjectAnimator.ofFloat(bg, "scaleX", 1, 1);
        final ObjectAnimator animScaleAY1 = ObjectAnimator.ofFloat(bg, "scaleY", 1, 1);
//        set5.playTogether(animScaleAX1, animScaleAY1, ObjectAnimator.ofFloat(startView, "translationY", 0, -42).setDuration(100));
        set5[0].playTogether(animScaleAX1, animScaleAY1, ObjectAnimator.ofFloat(bg, "translationY", 0, 0).setDuration(100)
                , ObjectAnimator.ofFloat(shape, "cornerRadius", 30 * dm.density, 10 * dm.density).setDuration(100));
        set5[0].setDuration(100);
        animScaleAX.setDuration(490 + 33 * (((ViewGroup) startLayout).getChildCount()));
        animScaleAY.setDuration(490 + 33 * (((ViewGroup) startLayout).getChildCount()));
        final ObjectAnimator animScaleBX = ObjectAnimator.ofFloat(startView, "scaleX", 1, 1);
        final ObjectAnimator animScaleBY = ObjectAnimator.ofFloat(startView, "scaleY", 1, 1);
        animScaleBX.setDuration(300);
        animScaleBY.setDuration(300);
        set3[0].playTogether(animScaleBX, animScaleBY);
        set3[0].setDuration(300);
        View layout = null;
//        final ObjectAnimator anim3 = ObjectAnimator.ofFloat(endView, "alpha", 0f, 1f);
        int count = ((ViewGroup) startView).getChildCount();
        ObjectAnimator obj = null;
        ObjectAnimator obj1;
//        View startViewLayout = ((ViewGroup) startView).getChildAt(1);
//        for (int i = 0; i < ((ViewGroup) startLayout).getChildCount(); i++) {
        for (int i = ((ViewGroup) startLayout).getChildCount() - 1; i >=0 ; i--) {
            obj = ObjectAnimator.ofFloat(((ViewGroup) startLayout).getChildAt(i), "translationY", 0, 132);
//            obj.setDuration(100);
            obj.setStartDelay(33 * (((ViewGroup) startLayout).getChildCount() - i));
            obj.setDuration(490 + 33 * (((ViewGroup) startLayout).getChildCount() - i));
            startAni.add(obj);
            obj = ObjectAnimator.ofFloat(((ViewGroup) startLayout).getChildAt(i), "alpha", 1, 0);
            obj.setDuration(490 + 33 * (((ViewGroup) startLayout).getChildCount() - i));
            obj.setStartDelay(33 * (((ViewGroup) startLayout).getChildCount() - i));
            startAni.add(obj);
//            obj = ObjectAnimator.ofFloat(((ViewGroup) startLayout).getChildAt(i), "scaleX", 1, 1);
//            obj.setDuration(490 + 33 * (((ViewGroup) startLayout).getChildCount() - i));
//            obj.setStartDelay(33 * (((ViewGroup) startLayout).getChildCount() - i));
//            startAni.add(obj);
//            obj = ObjectAnimator.ofFloat(((ViewGroup) startLayout).getChildAt(i), "scaleY", 1, 1);
//            obj.setDuration(490 + 33 * (((ViewGroup) startLayout).getChildCount() - i));
//            obj.setStartDelay(33 * (((ViewGroup) startLayout).getChildCount() - i));
//            startAni.add(obj);
        }
        set1[0].playTogether(startAni);
//        set1.setStartDelay(100);
        set1[0].setDuration(490 + 33 * (((ViewGroup) startLayout).getChildCount()));
//        set1.setStartDelay(100);
//        set1.addL
        set1[0].addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                startView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                set4[0].start();
                startView.setLayerType(View.LAYER_TYPE_NONE, null);
                set5[0].start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        set1[0].start();
        for (int i = 0; i < count; i++) {
            startAniFirst.add(ObjectAnimator.ofFloat(((ViewGroup) startView).getChildAt(i), "scaleY", 1, 1).setDuration(490 + 33 * (((ViewGroup) startLayout).getChildCount())));
            startAniFirst.add(ObjectAnimator.ofFloat(((ViewGroup) startView).getChildAt(i), "scaleX", 1, 1).setDuration(490 + 33 * (((ViewGroup) startLayout).getChildCount())));
        }
        startAniFirst.add(ObjectAnimator.ofFloat(shape, "cornerRadius", 10 * dm.density, 30 * dm.density).setDuration(490 + 33 * (((ViewGroup) startLayout).getChildCount())));
        if(isBig)
            startAniFirst.add(ObjectAnimator.ofFloat(bg, "translationY", 0, 42).setDuration(490 + 33 * (((ViewGroup) startLayout).getChildCount())));
        else
            startAniFirst.add(ObjectAnimator.ofFloat(bg, "translationY", 0, 25).setDuration(490 + 33 * (((ViewGroup) startLayout).getChildCount())));
        startAniFirst.add(animScaleAX);
        startAniFirst.add(animScaleAY);
//        set.playTogether(startAniFirst);
        count = ((ViewGroup) endView).getChildCount();
        /**
         * back
         * */
        View endLayout = ((ViewGroup) endView).getChildAt(1);
        for (int i = 0; i < ((ViewGroup) endLayout).getChildCount(); i++) {
            obj = ObjectAnimator.ofFloat(((ViewGroup) endLayout).getChildAt(i), "translationY", 66, 6);
            obj.setDuration(300);
            if (i == 2 || i == 3)
                obj.setStartDelay(100);
            endAni.add(obj);
            obj = ObjectAnimator.ofFloat(((ViewGroup) endLayout).getChildAt(i), "translationY", 6, 0);
            if (i == 2 || i == 3) {
                obj.setDuration(100);
                obj.setStartDelay(400);
            } else {
                obj.setDuration(100);
                obj.setStartDelay(300);
            }
            endAni.add(obj);
            obj = ObjectAnimator.ofFloat(((ViewGroup) endLayout).getChildAt(i), "alpha", 0, 1);
            obj.setDuration(500);
            endAni.add(obj);
//            obj = ObjectAnimator.ofFloat(((ViewGroup) endLayout).getChildAt(i), "scaleX", 1, 1);
//            obj.setDuration(500);
//            endAni.add(obj);
//            obj = ObjectAnimator.ofFloat(((ViewGroup) endLayout).getChildAt(i), "scaleY", 1, 1);
//            obj.setDuration(500);
//            endAni.add(obj);
        }
        set2[0].playTogether(endAni);
        set2[0].setDuration(500);
//        set2.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//                endView.setVisibility(View.VISIBLE);
//                startView.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                endAni.clear();
//                endView.clearAnimation();
//                startView.clearAnimation();
//                bg.clearAnimation();
////                set2 = null;
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
        Log.e("count", String.valueOf(((ViewGroup) endLayout).getChildCount()));
        for (int i = 0; i < ((ViewGroup) endLayout).getChildCount(); i++) {
//                    animll.getChildAt(i).setVisibility(View.GONE);
            obj = ObjectAnimator.ofFloat(((ViewGroup) endLayout).getChildAt(i), "translationY", 0, 66);
            obj.setDuration(10);
            endAni1.add(obj);
//                    obj[0] = ObjectAnimator.ofFloat(animll.getChildAt(i),"translationY",0,66);
        }
        set4[0].playTogether(endAni1);
        set4[0].setDuration(10);
        set4[0].addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
//                startView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                set2[0].start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

//        anim3.setDuration(300);
        set2[0].addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Log.e("set2start", "test");
                endView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//                startView.setVisibility(View.GONE);
//                startView.setVisibility(View.GONE);
//                endView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.e("set2end", "test");
                set3[0].start();
                startAni.clear();
                endAni.clear();
                endAni1.clear();
                endView.setLayerType(View.LAYER_TYPE_NONE, null);
                startView.clearAnimation();
                endView.clearAnimation();
                bg.clearAnimation();
                set1[0].end();
                set3[0].end();
                set5[0].end();
                set4[0].end();
                set1[0] = null;
                set2[0] = null;
                set3[0] = null;
                set5[0] = null;
                set4[0] = null;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
//        set1.playTogether(startAni);
//        set1.setDuration(300);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                startAni.clear();
//                set2.start();
//                bg.setImageDrawable(context.getApplicationContext().getResources().getDrawable(R.drawable.second_menu));
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
//        set1.setDuration(400);
//        set1.setStartDelay(100);
        set1[0].addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
//                startView.setVisibility(View.GONE);
//                set4.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set1[0].start();
        set.playTogether(startAniFirst);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
//                GradientDrawable shape = (GradientDrawable) startView.getBackground();

            }

            @Override
            public void onAnimationEnd(Animator animator) {
//                set3.start();
//                set5.start();
                startView.setVisibility(View.GONE);
                endView.setVisibility(View.VISIBLE);
                startAniFirst.clear();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.setDuration(490 + 33 * (((ViewGroup) startLayout).getChildCount()));
        set.start();
    }

    public static void SecondOutThirdIn(final View startView, final View endView, final float scaleX, final float scaleY,  final boolean isBig,Context context, final ImageView bg) {
        final int[] times = {0};
        boolean added = false;
        if(startView.getVisibility() == View.GONE){
            Log.e("visibility", "gone");
            startView.setVisibility(View.VISIBLE);
        }
        AnimatorSet set = new AnimatorSet();
        final AnimatorSet[] set1 = {new AnimatorSet()};
        final AnimatorSet[] set2 = {new AnimatorSet()};
        final AnimatorSet[] set3 = {new AnimatorSet()};
//        final AnimatorSet set4 = new AnimatorSet();
        final AnimatorSet[] set5 = {new AnimatorSet()};
        final ObjectAnimator animScaleAX1 = ObjectAnimator.ofFloat(bg, "scaleX", 1, 1);
        final ObjectAnimator animScaleAY1 = ObjectAnimator.ofFloat(bg, "scaleY", 1, 1);
        final ObjectAnimator animScaleAX = ObjectAnimator.ofFloat(bg, "scaleX", 1, scaleX);
        final ObjectAnimator animScaleAY = ObjectAnimator.ofFloat(bg, "scaleY", 1, scaleY);
        set5[0].playTogether(animScaleAX1, animScaleAY1);
        set5[0].setDuration(100);
        animScaleAX.setDuration(600);
        animScaleAY.setDuration(600);
        final ObjectAnimator animScaleBX = ObjectAnimator.ofFloat(startView, "scaleX", 1, 1);
        final ObjectAnimator animScaleBY = ObjectAnimator.ofFloat(startView, "scaleY", 1, 1);
        animScaleBX.setDuration(300);
        animScaleBY.setDuration(300);
        set3[0].playTogether(animScaleBX, animScaleBY);
        set3[0].setDuration(300);
//        View layout = null;
//        final ObjectAnimator anim3 = ObjectAnimator.ofFloat(endView, "alpha", 0f, 1f);
//        int count = ((ViewGroup) startView).getChildCount();
        int count = ((ViewGroup) ((ViewGroup) startView).getChildAt(1)).getChildCount();
        View layout = ((ViewGroup) startView).getChildAt(1);
        ObjectAnimator obj;
        Log.e("countSize",String.valueOf(count));
        ObjectAnimator obj1;
        for (int i = 0; i < count; i++) {
//            ((ViewGroup) layout).getChildAt(i).setVisibility(View.VISIBLE);
            obj = ObjectAnimator.ofFloat(((ViewGroup) layout).getChildAt(i), "translationY", 0, 66);
//            obj.setDuration(100);
            obj.setDuration(490 + 33 * (((ViewGroup) layout).getChildCount() - i));
            startAni.add(obj);
            obj = ObjectAnimator.ofFloat(((ViewGroup) layout).getChildAt(i), "alpha", 1, 0);
            obj.setDuration(490 + 33 * (((ViewGroup) layout).getChildCount() - i));
            startAni.add(obj);
//            obj = ObjectAnimator.ofFloat(((ViewGroup) layout).getChildAt(i), "scaleX", 1, 1);
//            obj.setDuration(490 + 33 * (((ViewGroup) layout).getChildCount() - i));
//            startAni.add(obj);
//            obj = ObjectAnimator.ofFloat(((ViewGroup) layout).getChildAt(i), "scaleY", 1, 1);
//            obj.setDuration(490 + 33 * (((ViewGroup) layout).getChildCount() - i));
//            startAni.add(obj);
        }
        set1[0].playTogether(startAni);
        set1[0].setDuration(490 + 33 * (((ViewGroup) layout).getChildCount()));
        set1[0].setStartDelay(100);
        set1[0].addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                startView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
//                startAni.clear();
                set2[0].start();
                startView.setLayerType(View.LAYER_TYPE_NONE,null);
//                set4.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set1[0].start();
        count = ((ViewGroup) startView).getChildCount();
        for (int i = 0; i < count; i++) {
//            startAniFirst.add(ObjectAnimator.ofFloat(((ViewGroup) startView).getChildAt(i), "scaleY", 1, 1).setDuration(600));
//            startAniFirst.add(ObjectAnimator.ofFloat(((ViewGroup) startView).getChildAt(i), "scaleX", 1, 1).setDuration(600));
        }
//        if(isBig)
//            startAniFirst.add(ObjectAnimator.ofFloat(bg, "translationY", 0, -42).setDuration(600));
//        else
//            startAniFirst.add(ObjectAnimator.ofFloat(bg, "translationY", 0, -25).setDuration(600));
        startAniFirst.add(animScaleAX);
        startAniFirst.add(animScaleAY);
//        set.playTogether(startAniFirst);
        /**
         * back
         * */
//        View endViewLayout = ((ViewGroup) endView).getChildAt(1);
//        View endLayout = ((ViewGroup) endView).getChildAt(1);
        View endLayout = endView;
        obj = ObjectAnimator.ofFloat(endLayout, "alpha", 0, 1);
        obj.setDuration(490);
        endAni.add(obj);
        obj = ObjectAnimator.ofFloat( endLayout, "scaleX", 1, 1);
        obj.setDuration(490);
        endAni.add(obj);
        obj = ObjectAnimator.ofFloat( endLayout, "scaleY", 1, 1);
        obj.setDuration(490);
        endAni.add(obj);
//        for (int i = 0; i < ((ViewGroup) endLayout).getChildCount(); i++) {
//            ((ViewGroup) endLayout).getChildAt(i).setVisibility(View.VISIBLE);
//            obj = ObjectAnimator.ofFloat(((ViewGroup) endLayout).getChildAt(i), "alpha", 0, 1);
//            obj.setStartDelay(33 * i);
//            obj.setDuration(490);
//            endAni.add(obj);
//            obj = ObjectAnimator.ofFloat(((ViewGroup) endLayout).getChildAt(i), "scaleX", 1, 1);
//            obj.setDuration(490);
//            obj.setStartDelay(33 * i);
//            endAni.add(obj);
//            obj = ObjectAnimator.ofFloat(((ViewGroup) endLayout).getChildAt(i), "scaleY", 1, 1);
//            obj.setDuration(490);
//            obj.setStartDelay(33 * i);
//            endAni.add(obj);
//        }
        endAni.add(ObjectAnimator.ofFloat(bg, "translationY", 0, 0).setDuration(2000));
        set2[0].playTogether(endAni);
        set2[0].setDuration(500);

//        anim3.setDuration(300);
        set2[0].addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Log.e("set2start", "test");
                startView.clearAnimation();
                startView.setVisibility(View.GONE);
//                startView.setVisibility(View.GONE);
                endView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
                endView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.e("set2end", "test");
                startAni.clear();
                endAni.clear();
                endAni1.clear();
                endView.setLayerType(View.LAYER_TYPE_NONE,null);
                bg.clearAnimation();
                endView.clearAnimation();
                set1[0].end();
                set3[0].end();
                set5[0].end();
                set1[0] = null;
                set2[0] = null;
                set3[0] = null;
                set5[0] = null;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
//        set1.playTogether(startAni);
//        set1.setDuration(300);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                startAni.clear();
//                set2.start();
//                    bg.setImageDrawable(context.getApplicationContext().getResources().getDrawable(R.drawable.menu_background));
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set1[0].setDuration(400);
        set1[0].setStartDelay(100);
        set1[0].start();
        set.playTogether(startAniFirst);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                set3[0].start();
                set5[0].start();
                startAniFirst.clear();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.setDuration(600);
        set.start();
    }

    public static void SecondInThirdOut(final View startView, final View endView, final float scaleX, final float scaleY,  final boolean isBig, final Context context, final ImageView bg) {
        if(bg.getVisibility() == View.GONE){
            Log.e("visibility","gone");
            bg.setVisibility(View.VISIBLE);
        }
        if(startView.getVisibility() == View.GONE){
            Log.e("visibility","gone");
            startView.setVisibility(View.VISIBLE);
        }
//        for (int i = 0; i < ((ViewGroup)startView).getChildCount(); i++) {
//            if(((ViewGroup) startView).getChildAt(i).getVisibility() == View.GONE){
//                Log.e("visibility",String.valueOf(i)+"gone");
//                ((ViewGroup) startView).getChildAt(i).setVisibility(View.VISIBLE);
//            }
//        }
        final AnimatorSet set = new AnimatorSet();
        final AnimatorSet set1 = new AnimatorSet();
        final AnimatorSet set2 = new AnimatorSet();
        final AnimatorSet set3 = new AnimatorSet();
//        final AnimatorSet set4 = new AnimatorSet();
        final AnimatorSet set5 = new AnimatorSet();
        final ObjectAnimator animScaleAX = ObjectAnimator.ofFloat(bg, "scaleX", 1, scaleX);
        final ObjectAnimator animScaleAY = ObjectAnimator.ofFloat(bg, "scaleY", 1, scaleY);
        final ObjectAnimator animScaleAX1 = ObjectAnimator.ofFloat(bg, "scaleX", 1, 1);
        final ObjectAnimator animScaleAY1 = ObjectAnimator.ofFloat(bg, "scaleY", 1, 1);
//        set5.playTogether(animScaleAX1, animScaleAY1, ObjectAnimator.ofFloat(startView, "translationY", 0, -42).setDuration(100));
        set5.playTogether(animScaleAX1, animScaleAY1, ObjectAnimator.ofFloat(bg, "translationY", 0, 0).setDuration(100));
        set5.playTogether( ObjectAnimator.ofFloat(bg, "translationY", 0, 0).setDuration(100));
        set5.setDuration(100);
        set5.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                startView.clearAnimation();
                startView.setLayerType(View.LAYER_TYPE_NONE,null);
                startView.setVisibility(View.GONE);
                endView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animScaleAX.setDuration(600);
        animScaleAY.setDuration(600);
        final ObjectAnimator animScaleBX = ObjectAnimator.ofFloat(startView, "scaleX", 1, 1);
        final ObjectAnimator animScaleBY = ObjectAnimator.ofFloat(startView, "scaleY", 1, 1);
        animScaleBX.setDuration(300);
        animScaleBY.setDuration(300);
        set3.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set3.playTogether(animScaleBX, animScaleBY);
        set3.setDuration(300);
        View layout = null;
//        final ObjectAnimator anim3 = ObjectAnimator.ofFloat(endView, "alpha", 0f, 1f);
//        int count = ((ViewGroup) startView).getChildCount();
        ObjectAnimator obj = null;
        ObjectAnimator obj1;
//        View startViewLayout = ((ViewGroup) startView).getChildAt(1);
//        View startLayout = ((ViewGroup) startView).getChildAt(1);
        obj = ObjectAnimator.ofFloat( startView, "alpha", 1, 0);
        obj.setDuration(490 );
        startAni.add(obj);
//        obj = ObjectAnimator.ofFloat( startView, "scaleX", 1, 1);
//        obj.setDuration(490);
//        startAni.add(obj);
//        obj = ObjectAnimator.ofFloat( startView, "scaleY", 1, 1);
//        obj.setDuration(490);
//        startAni.add(obj);
//        for (int i = 0; i < ((ViewGroup) startLayout).getChildCount(); i++) {
//        for (int i = ((ViewGroup) startLayout).getChildCount() - 1; i >=0 ; i--) {
//            obj = ObjectAnimator.ofFloat(((ViewGroup) startLayout).getChildAt(i), "alpha", 1, 0);
//            obj.setDuration(490 + 33 * (((ViewGroup) startLayout).getChildCount() - i));
//            obj.setStartDelay(33 * (((ViewGroup) startLayout).getChildCount() - i));
//            startAni.add(obj);
//            obj = ObjectAnimator.ofFloat(((ViewGroup) startLayout).getChildAt(i), "scaleX", 1, 1);
//            obj.setDuration(490 + 33 * (((ViewGroup) startLayout).getChildCount() - i));
//            obj.setStartDelay(33 * (((ViewGroup) startLayout).getChildCount() - i));
//            startAni.add(obj);
//            obj = ObjectAnimator.ofFloat(((ViewGroup) startLayout).getChildAt(i), "scaleY", 1, 1);
//            obj.setDuration(490 + 33 * (((ViewGroup) startLayout).getChildCount() - i));
//            obj.setStartDelay(33 * (((ViewGroup) startLayout).getChildCount() - i));
//            startAni.add(obj);
//        }
        set1.playTogether(startAni);
//        set1.setStartDelay(100);
        set1.setDuration(500);
//        set1.setStartDelay(100);
//        set1.addL
        set1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                startView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
//                set4.start();
                set5.start();
                set2.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        set1.start();
//        for (int i = 0; i < count; i++) {
//            startAniFirst.add(ObjectAnimator.ofFloat(((ViewGroup) startView).getChildAt(i), "scaleY", 1, 1).setDuration(490 + 33 * (((ViewGroup) startLayout).getChildCount())));
//            startAniFirst.add(ObjectAnimator.ofFloat(((ViewGroup) startView).getChildAt(i), "scaleX", 1, 1).setDuration(490 + 33 * (((ViewGroup) startLayout).getChildCount())));
//        }
//        if(isBig)
//            startAniFirst.add(ObjectAnimator.ofFloat(bg, "translationY", 0, 42).setDuration(490 + 33 * (((ViewGroup) startLayout).getChildCount())));
//        else
//            startAniFirst.add(ObjectAnimator.ofFloat(bg, "translationY", 0, 25).setDuration(490 + 33 * (((ViewGroup) startLayout).getChildCount())));
        startAniFirst.add(animScaleAX);
        startAniFirst.add(animScaleAY);
//        set.playTogether(startAniFirst);
//        int count = ((ViewGroup) endView).getChildCount();
        /**
         * back
         * */
        View endLayout = ((ViewGroup) endView).getChildAt(1);
        for (int i = 0; i < ((ViewGroup) endLayout).getChildCount(); i++) {
            obj = ObjectAnimator.ofFloat(((ViewGroup) endLayout).getChildAt(i), "translationY", 66, 0);
            obj.setStartDelay(33 * i);
            obj.setDuration(490);
            endAni.add(obj);
            obj = ObjectAnimator.ofFloat(((ViewGroup) endLayout).getChildAt(i), "alpha", 0, 1);
            obj.setDuration(500);
            endAni.add(obj);
            obj = ObjectAnimator.ofFloat(((ViewGroup) endLayout).getChildAt(i), "scaleX", 1, 1);
            obj.setDuration(500);
            endAni.add(obj);
            obj = ObjectAnimator.ofFloat(((ViewGroup) endLayout).getChildAt(i), "scaleY", 1, 1);
            obj.setDuration(500);
            endAni.add(obj);
        }
        set2.playTogether(endAni);
        set2.setDuration(500);
//        set2.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//                endView.setVisibility(View.VISIBLE);
//                startView.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                endAni.clear();
//                endView.clearAnimation();
//                startView.clearAnimation();
//                bg.clearAnimation();
////                set2 = null;
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
        Log.e("count", String.valueOf(((ViewGroup) endLayout).getChildCount()));

//        anim3.setDuration(300);
        set2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Log.e("set2start", "test");
                endView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
//                startView.setVisibility(View.GONE);
//                startView.setVisibility(View.GONE);
//                endView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.e("set2end", "test");
                set3.start();
                startAni.clear();
                endAni.clear();
                endAni1.clear();
                endView.setLayerType(View.LAYER_TYPE_NONE,null);
                endView.clearAnimation();
                bg.clearAnimation();
//                set1[0] = null;
//                set2[0] = null;
//                set5[0] = null;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
//        set1.playTogether(startAni);
//        set1.setDuration(300);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                startAni.clear();
//                set2.start();
//                bg.setImageDrawable(context.getApplicationContext().getResources().getDrawable(R.drawable.second_menu));
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
//        set1.setDuration(400);
//        set1.setStartDelay(100);
        set1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
//                startView.setVisibility(View.GONE);
//                set4.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set1.start();
        set.playTogether(startAniFirst);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
//                GradientDrawable shape = (GradientDrawable) startView.getBackground();

            }

            @Override
            public void onAnimationEnd(Animator animator) {
//                set3.start();
//                set5.start();
//                set2.start();
                startAniFirst.clear();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.setDuration(500);
        set.start();
    }


    /**
     * 设置控件的透明度
     *
     * @param view
     * @param startAlpha
     * @param endAlpha
     * @param time
     */
    public static void setAlpha2(final OnRotateEndListener onRotateEndListener, View view, float startAlpha, float endAlpha, int time) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "alpha", startAlpha, endAlpha);
        anim.setDuration(time);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (onRotateEndListener != null) {
                    onRotateEndListener.onRotateEnd();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        anim.start();
    }

    /**
     * 设置垂直位移
     *
     * @param view
     * @param startY
     * @param endY
     * @param time
     */
    public static void setTranslationY(final OnRotateEndListener onRotateEndListener, View view, float startY, float endY, int time) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "translationY", startY, endY);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (onRotateEndListener != null) {
                    onRotateEndListener.onRotateEnd();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        anim.setDuration(time);
        anim.start();
    }

    /**
     * 闪烁
     *
     * @param view
     */
    public static void setAlpaAnimation(View view) {
        AlphaAnimation alphaAnimation1 = new AlphaAnimation(0.7f, 1.0f);
        alphaAnimation1.setDuration(500);
        alphaAnimation1.setRepeatCount(Animation.INFINITE);
        alphaAnimation1.setRepeatMode(Animation.REVERSE);
        view.setAnimation(alphaAnimation1);
        alphaAnimation1.start();
    }

    /**
     * 旋转
     *
     * @param view
     * @param startRotationY
     * @param endRotationY
     * @param time
     */
    public static void setRotationY(View view, float startRotationY, float endRotationY, int time, final OnRotateEndListener onRotateEndListener) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation", startRotationY, endRotationY);
        anim.setDuration(time);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (onRotateEndListener != null) {
                    onRotateEndListener.onRotateEnd();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        anim.start();
    }

    public interface OnRotateEndListener {
        void onRotateEnd();
    }

    /**
     * 缩放
     *
     * @param view
     * @param startScale
     * @param endScale
     * @param time
     */
    public static void setScale(View view, float startScale, float endScale, int time) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(view, "scaleX", startScale, endScale),
                ObjectAnimator.ofFloat(view, "scaleY", startScale, endScale));
        set.setDuration(time);
        set.start();
    }

    public static void setClicked1(final View v){
        AnimatorSet set1 = new AnimatorSet();
        final AnimatorSet set2 = new AnimatorSet();
        ObjectAnimator objX1 = ObjectAnimator.ofFloat(v,"scaleX",1,1.1f);
        ObjectAnimator objY1 = ObjectAnimator.ofFloat(v,"scaleY",1,1.1f);
        final ObjectAnimator objX2 = ObjectAnimator.ofFloat(v,"scaleX",1.1f,1);
        final ObjectAnimator objY2 = ObjectAnimator.ofFloat(v,"scaleY",1.1f,1);
        set1.playTogether(objX1,objY1);
        set1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                set2.playTogether(objX2,objY2);
                set2.setDuration(150);
                set2.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set1.setDuration(150);
        set1.start();
    }

    public static void setClicked(final View v) {
//        AnimatorSet set1 = new AnimatorSet();
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(150);
        final ScaleAnimation scaleAnimation1 = new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation1.setDuration(150);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                scaleAnimation1.startNow();
                scaleAnimation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        v.clearAnimation();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.setAnimation(scaleAnimation);
//       ValueAnimator obj = ObjectAnimator.ofFloat(v, "scaleX", 1, 1 / 1f).setDuration(150);
//        obj.setStartDelay(150);
//        ObjectAnimator obj1 = ObjectAnimator.ofFloat(v, "scaleY", 1, 1 / 1f).setDuration(150);
//        obj1.setStartDelay(150);
//        set1.playTogether(ObjectAnimator.ofFloat(v, "scaleX", 1, 1.1f).setDuration(150),
//                ObjectAnimator.ofFloat(v, "scaleY", 1, 1.1f).setDuration(150),obj,obj1);
//        set1.setDuration(300);
//        set1.setInterpolator(new AccelerateDecelerateInterpolator());
//        set1.start();
//        set1.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                v.clearAnimation();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
    }

    public static void setScale(View view, float startScale, float endScale, int time, ObjectAnimator anim) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(view, "scaleX", startScale, endScale),
                ObjectAnimator.ofFloat(view, "scaleY", startScale, endScale), anim);
        set.setDuration(time);
        set.start();
    }

    public static void setLayoutTranslate(final View view, float startY, float endY, int time) {
        ObjectAnimator anim;
        anim = ObjectAnimator.ofFloat(view, "translationY", startY, endY);
        anim.setDuration(time);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.clearAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public static void setViewTranslate(View view, float startY, float height, int time, boolean topToBottom) {
        AnimatorSet set = new AnimatorSet();
        int count = ((ViewGroup) view).getChildCount();
        ObjectAnimator obj;
        for (int i = 0; i < count; i++) {
            if (topToBottom)
                obj = ObjectAnimator.ofFloat(((ViewGroup) view).getChildAt(i), "translationY", startY, startY + height).setDuration(time);
            else
                obj = ObjectAnimator.ofFloat(((ViewGroup) view).getChildAt(i), "translationY", startY, startY - height).setDuration(time);
            obj.setInterpolator(new AccelerateDecelerateInterpolator());
            anims.add(obj);
        }
        set.playTogether(anims);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                anims.clear();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.setDuration(time);
        set.start();
    }

    /**
     * 设置位移和透明度
     *
     * @param view
     * @param startY
     * @param endY
     * @param startAlpha
     * @param endAlpha
     * @param time
     */
    public static void setTranslateAndAlpha(final AnimatorEndListener listener, View view, float startY, float endY, float startAlpha, final float endAlpha, int time) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(view, "translationY", startY, endY),
                ObjectAnimator.ofFloat(view, "alpha", startAlpha, endAlpha));
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (endAlpha == 0) {
                    listener.setOnAnimatorEndListener();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.setDuration(time);
        set.start();
    }

    /**
     * 设置控件的缩放动画2
     */
    public static void setScale2(final AnimatorEndListener listener, final View view, float startScale, float endScale, int time) {
        AnimatorSet set = new AnimatorSet();
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(startScale, endScale);
        valueAnimator.setDuration(time);
//        view.setPivotX(0);
//        view.setPivotY(0);
        valueAnimator.setTarget(view);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (Float) animation.getAnimatedValue();
                view.setScaleX(scale);
                view.setScaleY(scale);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (listener != null) {
                    listener.setOnAnimatorEndListener();
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
     * 动画播放完成的监听器接口
     */
    public interface AnimatorEndListener {
        void setOnAnimatorEndListener();
    }

}
