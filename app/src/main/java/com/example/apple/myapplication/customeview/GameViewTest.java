package com.example.apple.myapplication.customeview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fazhao on 16/9/1.
 */
public class GameViewTest extends Activity {
    private int screenH,screenW;
    private int racketY;
    private final int RACKECT_HEIGHT = 20;
    private final int RACKECT_WIDTH = 70;
    private final int BALL_SIZ = 12;
    private int ySeed = 10;
    Random random = new Random();
    private double xyRate = random.nextDouble() - 0.5;
    private int xSpeed = (int) (ySeed * xyRate *2);
    private int ballX= random.nextInt(200) + 20;
    private  int ballY = random.nextInt(10) + 20;
    private int rackectX = random.nextInt(200);
    private boolean isLose = false;
    private float downX = 0;
    private float downY = 0;
    private float MoveX = 0;
    private float MoveY = 0;
    private float DragX = 0;
    private float DragY = 0;
    private float previousY = 0;
    private float previousX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final GameView gameView = new GameView(this);
        setContentView(gameView);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        screenH = dm.heightPixels;
        screenW = dm.widthPixels;
        racketY = screenH - 80;
        final Handler handler = new android.os.Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 0x123)
                    gameView.invalidate();
            }
        };
//        gameView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        MoveX = motionEvent.getX();
//                        MoveY = motionEvent.getY();
//                        DragX = MoveX - downX;
//                        DragY = MoveY - downY;
//                        gameView.layout((int) (gameView.getLeft() + DragX), (int) gameView.getTop(), (int)
//                                (gameView.getLeft() + DragX + gameView.getWidth()), (int)
//                                (gameView.getTop() + DragY + gameView.getHeight()));
//                        previousX = DragX;
//                        previousY = DragY;
//                    case MotionEvent.ACTION_UP:
//                        break;
//
//                }
//                return false;
//            }
//        });

        gameView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                switch (keyEvent.getKeyCode()){
                    /**
                    *  keyboard:(A)left
                    * */
                    case KeyEvent.KEYCODE_A:
                        if(rackectX >0)
                            rackectX -= 10;
                        break;
                    /**
                    * keyboard:(D)right
                    * */
                    case KeyEvent.KEYCODE_D:
                        if(rackectX < screenW - RACKECT_WIDTH)
                            rackectX+=10;
                        break;
                }
                gameView.invalidate();
                return true;
            }
        });
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (ballX <= 0 || ballX >= screenW - BALL_SIZ) {
                    xSpeed = -xSpeed;
                }
                if (ballY >= racketY - BALL_SIZ
                        && (ballX < rackectX || ballX > rackectX + RACKECT_WIDTH)) {
                    timer.cancel();
                    isLose = true;
                } else if (ballY <= 0
                        || ballY >= racketY - BALL_SIZ
                        && ballX > rackectX && ballX <= rackectX + RACKECT_WIDTH)
                    ySeed = -ySeed;
                ballX += xSpeed;
                ballY += ySeed;
                handler.sendEmptyMessage(0x123);
            }
        }, 0, 100);
    }

    class GameView extends View {
        Paint paint = new Paint();
        public GameView(Context context) {
            super(context);
            setFocusable(true);
        }

        public GameView(Context context, AttributeSet attrs) {
            super(context, attrs);
            setFocusable(true);
        }

        public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            setFocusable(true);
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    downX = motionEvent.getX();
                    downY = motionEvent.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    MoveX = motionEvent.getX();
                    MoveY = motionEvent.getY();
                    DragX = MoveX - downX;
                    DragY = MoveY - downY;
                    this.layout((int) (this.getLeft() + DragX), (int) this.getTop(), (int)
                            (this.getLeft() + DragX + this.getWidth()), (int)
                            (this.getTop() + DragY + this.getHeight()));
                    previousX = DragX;
                    previousY = DragY;
                case MotionEvent.ACTION_UP:
                    break;

            }
            return false;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            if(isLose){
                paint.setColor(Color.RED);
                paint.setTextSize(40);
                canvas.drawText("Game Over",50,200,paint);
            }else{
                paint.setColor(Color.BLUE);
                canvas.drawCircle(ballX, ballY, BALL_SIZ, paint);
                paint.setColor(Color.GRAY);
                canvas.drawRect(rackectX,racketY,rackectX+RACKECT_WIDTH,racketY+RACKECT_HEIGHT,paint);
            }
        }
    }
}
