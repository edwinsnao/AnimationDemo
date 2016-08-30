package com.example.apple.myapplication;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.apple.myapplication.customeview.CustomTextView;
import com.example.apple.myapplication.customeview.DragAdapter;
import com.example.apple.myapplication.customeview.DragGridView;
import com.example.apple.myapplication.customeview.LocationLayout;
import com.example.apple.myapplication.customeview.MyTextView;
import com.example.apple.myapplication.customeview.OperationView;
import com.example.apple.myapplication.customeview.TextAlign;
import com.example.apple.myapplication.utils.AnimationUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OperationView.InflateEnd{
    View view1;
    View view2;
    View view3;
    View view4;
    View view5;
    LocationLayout location;
    LinearLayout firstLL, secondLL,main;
    TextView tv, first, second,demo,tmpTv;
    int i = 0;
    int h;
    int tmp = 0;
    DisplayMetrics dm;
    CustomTextView test;
    Button enable,enable1;
    public static TextView xy;
    private float oldX = 1,oldY = 1;
    OperationView operationView;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        setContentView(R.layout.activity_main1);
//        setContentView(R.layout.activity_main2);
//        setContentView(R.layout.activity_main4);
//        setContentView(R.layout.activity_main3);
        setContentView(R.layout.activity_main5);
        dm = getResources().getDisplayMetrics();
        h = (int) (50 * dm.density);
//        LargeImageView img = (LargeImageView) findViewById(R.id.img);
//        String path = "/sdcard/100385.png";
//        File file = new File(path);
////        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"100385.png");
//        InputStream is = null;
//        try {
//            is = new FileInputStream(file);
//            img.setInputStream(is);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        finally {
//            try {
//                is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        initLocation();

//        initView();
//        initListener();
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        initOperateView();
        initDragView();

    }


    /**
    * pointToPosition Usage(GridView and ListView)
    * */
    private void initDragView() {
        DragGridView grid = (DragGridView) findViewById(R.id.drag);
        List<String> names = new ArrayList<>();
        names.add("Hello");
        names.add("Hello");
        names.add("Hello");
        names.add("Hello");
        names.add("Hello");
        names.add("Hello");
        DragAdapter adapter = new DragAdapter(this,names);
        grid.setAdapter(adapter);
    }

    private void initOperateView() {
        frameLayout = (FrameLayout) findViewById(R.id.root);
        xy = (TextView) findViewById(R.id.xy);
        operationView = (OperationView) findViewById(R.id.operate);
//        operationView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                operationView.scale();
//            }
//        });
    }

    private void initImageView() {
//        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final ImageView imageView = (ImageView) findViewById(R.id.img);
        operationView.buildDrawingCache();
//        Bitmap tmp = operationView.getDrawingCache();
//        int w = View.MeasureSpec.makeMeasureSpec(0,
//                View.MeasureSpec.UNSPECIFIED);
//        int h = View.MeasureSpec.makeMeasureSpec(0,
//                View.MeasureSpec.UNSPECIFIED);
//        operationView.measure(w, h);
//        int width = operationView.getMeasuredWidth();
//        int height = operationView.getMeasuredHeight();
        Log.e("w-h:",new StringBuilder().append(OperationView.w).append(OperationView.h).toString());
//        Bitmap bitmap = Bitmap.createBitmap(OperationView.w,OperationView.h, Bitmap.Config.ARGB_8888);
        Bitmap bitmap = operationView.getDrawingCache();
        imageView.setImageBitmap(bitmap);
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
//        imageView.setDrawingCacheEnabled(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operationView.buildDrawingCache();
                Bitmap bitmap = Bitmap.createBitmap(operationView.getDrawingCache());
                imageView.setImageBitmap(bitmap);
                imageView.setScaleX(oldX * 2);
                imageView.setScaleY(oldY * 2);
            }
        });
//        frameLayout.addView(imageView, lp);
    }

    private void initLocation() {
        demo = (TextView) findViewById(R.id.demo);
        enable1 = (Button) findViewById(R.id.enable);
        enable1.setBackgroundColor(Color.WHITE);
        enable1.setTextColor(Color.BLACK);
        enable1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (++tmp % 2 == 1) {
                    demo.setVisibility(View.GONE);
                } else {
                    demo.setVisibility(View.VISIBLE);
                }
            }
        });
        location = (LocationLayout) findViewById(R.id.location);
        for (int i = 0; i < location.getChildCount(); i++) {

            Log.e("child", String.valueOf(location.getChildAt(i)));
        }
        MyTextView my = new MyTextView(this);
        my.setText("MyTextView");
        my.setTextSize(50);
        my.setTextColor(Color.WHITE);
        test = new CustomTextView(this);
        test.setId(1);
        test.setText("hello");
        test.setEnabled(true);
        test.setTextColor(Color.WHITE);
        test.setTextSize(50);
        Log.e("test", String.valueOf(test.getRootView()));
        /**
        * 返回viewParent null
        * */
//        Log.e("test",String.valueOf(test.getParent()));
//        Log.e("test",String.valueOf(tmpTv.getRootView()));
//        Log.e("test",String.valueOf(tmpTv.getParent()));
        tmpTv = new TextView(this);
        tmpTv.setText("tmp");
        tmpTv.setId(3);
        tmpTv.setEnabled(true);
        tmpTv.setTextColor(Color.WHITE);
        tmpTv.setTextSize(50);
        enable = new Button(this);
        enable.setId(2);
        enable.setText("visibility");
        enable.setBackgroundColor(Color.WHITE);
        enable.setTextColor(Color.BLACK);
        /**
        * 发现了原来是visibility导致locationLayout重绘了,丢失之前的位置了
        * */
        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (++tmp % 2 == 1) {
                    test.setVisibility(View.GONE);
                } else {
                    test.setVisibility(View.VISIBLE);
                }
            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.addRule(RelativeLayout.BELOW,1);
        lp1.addRule(RelativeLayout.CENTER_IN_PARENT);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.BELOW,2);
        lp2.addRule(RelativeLayout.CENTER_IN_PARENT);
        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.BELOW,3);
        lp2.addRule(RelativeLayout.CENTER_IN_PARENT);
        location.addView(test, lp);
        location.addView(enable, lp1);
        location.addView(tmpTv, lp2);
        location.addView(my, lp3);
        for (int i = 0; i < location.getChildCount(); i++) {

            Log.e("child", String.valueOf(location.getChildAt(i)));
        }
    }

    public void initListener() {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        tv.setText("$" + valueAnimator.getAnimatedValue());
                        Log.e("fraction", String.valueOf(valueAnimator.getAnimatedFraction()));
                        Log.e("current", String.valueOf(valueAnimator.getCurrentPlayTime()));
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
                /**
                 * animator和animation不能同时进行?
                 * 下面的setclicked是animation的,但是运行时发现是先animator执行完再animation
                 * 使用animator就可以一起进行了
                 * */
                AnimationUtils.setClicked1(view1);
//                AnimationUtils.setClicked(view1);
                i++;
                if (i % 2 == 1) {
                    ObjectAnimator obj = ObjectAnimator.ofFloat(view1, "rotation", 0, 100).setDuration(500);
                    obj.start();
                    transalteX(view2, 0, (int) (-60 * dm.density), 0, 1);
                    transalteX(view3, 0, (int) (60 * dm.density), 0, 1);
                    transalteY(view4, 0, (int) (-60 * dm.density), 0, 1);
                    transalteY(view5, 0, (int) (60 * dm.density), 0, 1);
                } else if (i % 2 == 0) {
                    ObjectAnimator obj1 = ObjectAnimator.ofFloat(view1, "rotation", 100, 0).setDuration(500);
                    obj1.start();
                    transalteX(view2, (int) (-60 * dm.density), 0, 1, 0);
                    transalteX(view3, (int) (60 * dm.density), 0, 1, 0);
                    transalteY(view4, (int) (-60 * dm.density), 0, 1, 0);
                    transalteY(view5, (int) (60 * dm.density), 0, 1, 0);
                }
            }
        });
        firstLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (secondLL.getVisibility() == View.GONE)
                    show();
                else
                    hide();
            }
        });
    }

    public ValueAnimator DropText(final View v, int start, int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams lp = v.getLayoutParams();
                lp.height = value;
                v.setLayoutParams(lp);
            }
        });
        return valueAnimator;
    }

    public void show() {
        secondLL.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = DropText(secondLL, 0, h);
        valueAnimator.start();
    }

    public void hide() {
        ValueAnimator valueAnimator = DropText(secondLL, h, 0);
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                secondLL.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        valueAnimator.start();
    }

    public void transalteX(final View v, int startX, int endX, int startAlpha, int endAlpha) {
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

    public void transalteY(final View v, int startY, int endY, int startAlpha, int endAlpha) {
        final AnimatorSet[] set = {new AnimatorSet()};
//        ObjectAnimator obj = ObjectAnimator.ofArgb()
        ObjectAnimator obj = ObjectAnimator.ofFloat(v, "translationY", startY, endY);
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

    public void initView() {
        view1 = findViewById(R.id.viewPre1);
        view2 = findViewById(R.id.viewPre2);
        view3 = findViewById(R.id.viewPre3);
        view4 = findViewById(R.id.viewPre4);
        view5 = findViewById(R.id.viewPre5);
        tv = (TextView) findViewById(R.id.tv);
        first = (TextView) findViewById(R.id.first);
        second = (TextView) findViewById(R.id.second);
        firstLL = (LinearLayout) findViewById(R.id.firstll);
        secondLL = (LinearLayout) findViewById(R.id.secondll);
        main = (LinearLayout) findViewById(R.id.main);
//        Circle circle = new Circle(this);
        TextAlign text = new TextAlign(this);
//        main.addView(circle);
        main.addView(text);
    }

    public void setVisibility() {
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
            test.setEnabled(true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void inflate() {
        initImageView();
    }
}
