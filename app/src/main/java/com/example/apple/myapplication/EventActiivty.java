package com.example.apple.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apple.myapplication.utils.AnimationUtils;

/**
 * Created by fazhao on 2016/10/8.
 */

public class EventActiivty extends Activity {
    TextView on,down;
    LinearLayout onll,downll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        initView();
    }

    private void initView() {
        on = (TextView) findViewById(R.id.on);
        down = (TextView) findViewById(R.id.down);
        onll = (LinearLayout) findViewById(R.id.onll);
        downll = (LinearLayout) findViewById(R.id.downll);
        down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                AnimationUtils.setClicked1(down);
                return false;
            }
        });
        on.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                AnimationUtils.setClicked1(on);
                return false;
            }
        });
    }
}
