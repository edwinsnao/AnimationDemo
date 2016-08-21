package com.example.apple.myapplication;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    View view1;
    View view2;
    View view3;
    View view4;
    View view5;
    TextView tv;
    int i = 0;
    DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dm = getResources().getDisplayMetrics();

        initView();
        initListener();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void initListener(){
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValueAnimator valueAnimator = ValueAnimator.ofInt(0,100);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        tv.setText("$" + valueAnimator.getAnimatedValue());
                        Log.e("fraction",String.valueOf(valueAnimator.getAnimatedFraction()));
                        Log.e("current",String.valueOf(valueAnimator.getCurrentPlayTime()));
                    }
                });
                valueAnimator.setDuration(3000);
                valueAnimator.start();
                valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            }
        });
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                if(i % 2 == 1) {
                    ObjectAnimator obj = ObjectAnimator.ofFloat(view1, "rotation", 0, 100).setDuration(500);
                    obj.start();
                    transalteX(view2, 0, (int) (-60 * dm.density), 0, 1);
                    transalteX(view3,0, (int) (60 * dm.density),0,1);
                    transalteY(view4,0, (int) (-60 * dm.density),0,1);
                    transalteY(view5,0, (int) (60 * dm.density),0,1);
                }
                else if(i % 2 == 0){
                    ObjectAnimator obj1 = ObjectAnimator.ofFloat(view1, "rotation", 100, 0).setDuration(500);
                    obj1.start();
                    transalteX(view2, (int) (-60 * dm.density), 0, 1, 0);
                    transalteX(view3, (int) (60 * dm.density),0,1,0);
                    transalteY(view4, (int) (-60 * dm.density) ,0,1,0);
                    transalteY(view5, (int) (60 * dm.density),0,1,0);
                }
            }
        });
    }

    public void transalteX(final View v,int startX,int endX,int startAlpha,int endAlpha){
        final AnimatorSet[] set = {new AnimatorSet()};
//        ObjectAnimator obj = ObjectAnimator.ofArgb()
        ObjectAnimator obj = ObjectAnimator.ofFloat(v, "translationX", startX, endX);
        ObjectAnimator obj1 = ObjectAnimator.ofFloat(v, "alpha", startAlpha, endAlpha);
        set[0].playTogether(obj, obj1);
        set[0].setDuration(500);
        set[0].start();
        set[0].addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                set[0] = null;
                v.clearAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public void transalteY(final View v,int startY,int endY,int startAlpha,int endAlpha){
        final AnimatorSet[] set = {new AnimatorSet()};
//        ObjectAnimator obj = ObjectAnimator.ofArgb()
        ObjectAnimator obj = ObjectAnimator.ofFloat(v, "translationY", startY, endY);
        ObjectAnimator obj1 = ObjectAnimator.ofFloat(v,"alpha",startAlpha,endAlpha);
        set[0].playTogether(obj, obj1);
        set[0].setDuration(500);
        set[0].start();
        set[0].addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                set[0] = null;
                v.clearAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public void initView(){
        view1 = findViewById(R.id.viewPre1);
        view2 = findViewById(R.id.viewPre2);
        view3 = findViewById(R.id.viewPre3);
        view4 = findViewById(R.id.viewPre4);
        view5 = findViewById(R.id.viewPre5);
        tv = (TextView) findViewById(R.id.tv);
    }

    public void setVisibility(){
//        view2.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
