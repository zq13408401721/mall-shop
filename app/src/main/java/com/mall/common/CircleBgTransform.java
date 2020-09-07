package com.mall.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.mall.R;


import java.security.MessageDigest;

public class CircleBgTransform extends BitmapTransformation {
    private Paint mBorderPaint;
    private float mBorderWidth;

    private Context context;
    private int size;

    public CircleBgTransform(Context context){
        super();
    }

    public CircleBgTransform(Context context, int borderWidth){
        this.context = context;
        mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;

        mBorderPaint = new Paint();
        mBorderPaint.setDither(true);
        mBorderPaint.setAntiAlias(true);

    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool,toTransform);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }

    /**
     * 剪切图片并添加渐变背景
     * @param pool
     * @param source
     * @return
     */
    private Bitmap circleCrop(BitmapPool pool, Bitmap source){
        if(source == null) return null;
        size = Math.min(source.getWidth(),source.getHeight());
        //圆形图
        Bitmap bmp = Bitmap.createBitmap(size,size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);

        //修改paint画笔的渐变色 参数(x0,y0,x1,y1) 表示渐变开始和结束位置的坐标
        LinearGradient linearGradient = new LinearGradient(0,size,size,0,new int[]{Color.parseColor("#FFFFA4BE"), Color.WHITE},new float[]{.3f,1}, Shader.TileMode.CLAMP);
        mBorderPaint.setShader(linearGradient);
        //参数1，2 表示圆心坐标（x,y)
        canvas.drawCircle(size/2,size/2,size/2,mBorderPaint);

        //获取图片
        Bitmap bitmap = ((BitmapDrawable)context.getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap();

        //canvas.drawBitmap(bitmap,0,0,paint);

        //创建一张新的图片
        Bitmap circle = Bitmap.createBitmap(size-20,size-20, Bitmap.Config.ARGB_8888);
        //创建一张新的画布
        Canvas cas = new Canvas(circle);
        cas.drawCircle((size-20)/2+10,(size-20)/2+10,(size-20)/2,mBorderPaint);
        //取圆和bitmap的交集
        mBorderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //把bitmap图片的内容绘制circle里面
        cas.drawBitmap(source,0,0,mBorderPaint);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawBitmap(circle,0,0,paint);

        return bmp;


        /*int size = Math.min(source.getWidth(),source.getHeight());
        int x = (source.getWidth()-size)/2;
        int y = (source.getHeight()-size)/2;
        Bitmap squared = Bitmap.createBitmap(source,x,y,size,size);
        Bitmap result = pool.get(size,size,Bitmap.Config.ARGB_8888);
        if(result == null){
            result = Bitmap.createBitmap(size,size,Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared,BitmapShader.TileMode.CLAMP,BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);

        //绘制边框
        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(mBorderWidth);
        borderPaint.setColor(Color.WHITE);//边框颜色
        borderPaint.setStrokeCap(Paint.Cap.ROUND);
        borderPaint.setAntiAlias(true);
        float r = size/2f;
        float r1 = (size-2*4)/2f;
        canvas.drawCircle(r,r,r1,paint);
        canvas.drawCircle(r,r,r1,borderPaint);
        return result;*/
    }
}
