package com.example.homework_day_08.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

public class LineEditTextView extends AppCompatEditText {

    private Paint mPaint;
    private Rect mRect;

    public LineEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LineEditTextView(Context context) {
        super(context);
    }

    public LineEditTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int count = getLineCount();
        for (int i=0; i < count; i++) {
            int baseline = getLineBounds(i, mRect);
            canvas.drawLine(
                    mRect.left, baseline + 1, mRect.right, baseline + 1,
                    mPaint);
        }
        super.onDraw(canvas);
    }
}
