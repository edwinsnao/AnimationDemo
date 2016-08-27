package com.example.apple.myapplication.customeview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by fazhao on 16/8/24.
 */
public class LargeImageView extends View {
    private BitmapRegionDecoder mDecoder;
    private int mWidth,mHeight;
    private volatile Rect mRect = new Rect();
    private static final BitmapFactory.Options options = new BitmapFactory.Options();

    static{
        options.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bm = mDecoder.decodeRegion(mRect,options);
        canvas.drawBitmap(bm,0,0,null);
    }

    public void setInputStream(InputStream is ){
        try{
            mDecoder = BitmapRegionDecoder.newInstance(is,false);
            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
            tmpOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is,null,tmpOptions);
            mWidth = tmpOptions.outWidth;
            mHeight = tmpOptions.outHeight;

            requestLayout();
            invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            {
                try{
                    if(is!=null)
                        is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void init(){
    }

    private void checkWidth(){
        Rect rect = mRect;
        int imageWidth = mWidth;
        int imageHeight = mHeight;

        if(rect.right>imageWidth){
            rect.right = imageWidth;
            rect.left = rect.right - getWidth();
        }

        if(rect.left < 0){
            rect.left = 0;
            rect.right = getWidth();
        }
    }

    private void checkHeight(){
        Rect rect = mRect;
        int imageWidth = mWidth;
        int imageHeight = mHeight;

        if(rect.bottom>imageHeight){
            rect.bottom = imageHeight;
            rect.top = rect.bottom - getHeight();
        }

        if(rect.top < 0){
            rect.top = 0;
            rect.bottom = getHeight();
        }
    }

    public LargeImageView(Context context) {
        super(context);
    }

    public LargeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LargeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int imageWidth = mWidth;
        int imageHeight = mHeight;

        mRect.left = imageWidth  / 2 - width / 2;
        mRect.top = imageHeight / 2 - height / 2;
        mRect.right = mRect.left + width;
        mRect.bottom = mRect.top + height;

    }
}
