package com.example.apple.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apple.myapplication.customeview.MyOnTextView;
import com.example.apple.myapplication.customeview.MyTextView;
import com.example.apple.myapplication.utils.AnimationUtils;

/**
 * Created by fazhao on 2016/10/8.
 */

public class EventActiivty extends Activity {
    TextView on,down,on1,on2;
    LinearLayout onll,downll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        initView();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("ActivityTouchEvent",""+event.getAction());
//        switch (event.getAction()){
//            case MotionEvent.ACTION_MOVE:
//                on1.onTouchEvent(event);
//                break;
//        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("Activitydispatch",""+ev.getAction());
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
//                on1.dispatchTouchEvent(ev);
//                on1.onTouchEvent(ev);
//                return super.dispatchTouchEvent(ev);
//                return true;
                return on1.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void initView() {
        on = (MyOnTextView) findViewById(R.id.on);
        on1 = (MyOnTextView) findViewById(R.id.on1);
        on2 = (MyOnTextView) findViewById(R.id.on2);
        down = (MyTextView) findViewById(R.id.down);
        onll = (LinearLayout) findViewById(R.id.onll);
        downll = (LinearLayout) findViewById(R.id.downll);
//        down.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                AnimationUtils.setClicked1(down);
//                return false;
//            }
//        });
//        on.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                AnimationUtils.setClicked1(on);
//                return false;
//            }
//        });
//        on1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                AnimationUtils.setClicked1(on1);
//                return false;
//            }
//        });
    }
}
