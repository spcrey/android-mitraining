package com.example.homework_day_08.view;

import android.content.Context;
import android.content.pm.Attribution;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.homework_day_08.R;

public class TestView extends View {

    private final Paint mPaint;


    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        addText(canvas);
        addLine(canvas);
        addRectangle(canvas);
        addCircle(canvas);
        addClipCanvas(canvas);
        addPoint(canvas);
        addImage(canvas);
        addPath(canvas);
    }

    private void addPath(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        Path path = new Path();
        path.moveTo(400,100);
        path.lineTo(1000,150);
        path.moveTo(400,200);
        path.quadTo(650,200,400,400);
        path.moveTo(400,600);
        path.cubicTo(400,500,500,350,600,700);
        path.moveTo(600,1000);
        RectF mrectF = new RectF(400,700,600,900);
        path.arcTo(mrectF,0,170);
        canvas.drawPath(path, mPaint);
    }

    private void addImage(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(
                getResources(), R.drawable.android_icon
        );
        canvas.drawBitmap(bitmap, 500, 200, mPaint);
    }

    private void addPoint(Canvas canvas) {
        canvas.drawPoint(400, 200, mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawPoints(new float[]{
                420, 220, 460, 200, 480, 210
        }, mPaint);
    }



    private void addClipCanvas(Canvas canvas) {
        canvas.save();
        mPaint.setStyle(Paint.Style.FILL);
        canvas.clipRect(new Rect(100,500,300,250));
        canvas.drawColor(Color.LTGRAY);
        canvas.drawCircle(150,200,100,mPaint);
        canvas.restore();
    }

    private void addRectangle(Canvas canvas) {
        canvas.drawRect(700, 20, 900, 100, mPaint);
    }

    private void addCircle(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(600, 70, 50, mPaint);
    }

    private void addLine(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        canvas.drawLine(100, 100, 300, 100, mPaint);
    }

    void addText(Canvas canvas) {
        mPaint.setTextSize(60);
        mPaint.setColor(Color.WHITE);
        canvas.drawText("start write", 100, 80, mPaint);
    }
}
