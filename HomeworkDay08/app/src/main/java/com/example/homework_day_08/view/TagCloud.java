package com.example.homework_day_08.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.homework_day_08.R;
import com.example.homework_day_08.tools.Location;

import java.util.List;

public class TagCloud extends FrameLayout {

    private final String TAG = "TagCloud";
    private final float mHorizontalMargin;
    private final float mVerticalMargin;
    private final float mTextSize;
    private final int mTextPadding;
    GestureDetector mDetector;
    private TextView mSelectedView = null;
    private Integer mSelectIndex = null;

    private void selectView(TextView textView, int index) {
        mSelectedView = textView;
        mSelectIndex = index;
        ScaleAnimation sa = new ScaleAnimation(
                1, 1.2f, 1, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
                );
        sa.setDuration(200);
        sa.setFillAfter(true);
        textView.startAnimation(sa);
    }

    private void releaseView(MotionEvent event) {
        if (mSelectedView != null) {
            mSelectedView.clearAnimation();
            mSelectedView.setTranslationX(0);
            mSelectedView.setTranslationY(0);
            reLayout(event);
            mSelectedView = null;
            mSelectIndex = null;
        }
    }

    private void reLayout(MotionEvent e) {
        RectF rectF = new RectF();
        for (int i=0; i<getChildCount(); i++) {
            TextView textView = (TextView)getChildAt(i);
            rectF.set(textView.getLeft(), textView.getTop(), textView.getRight(), textView.getBottom());
            if (rectF.contains(e.getX(), e.getY())) {
                Log.d(TAG, "click " + textView.getText());
                if (mSelectIndex == i) {
                    Log.d(TAG, "no change");
                } else {
                    Log.d(TAG, "need change");
                    View tmpView = getChildAt(mSelectIndex);
                    removeView(getChildAt(mSelectIndex));
                    addView(tmpView, i);
                    requestLayout();
                }
                break;
            }
            else {
                Log.d(TAG, "no change");
            }
        }
    }

    private void mDetectorInit() {
        mDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(@NonNull MotionEvent e) {
                Log.d(TAG, "onDown");
                return true;
            }

            @Override
            public void onShowPress(@NonNull MotionEvent e) {
                Log.d(TAG, "onShowPress");
            }

            @Override
            public boolean onSingleTapUp(@NonNull MotionEvent e) {
                Log.d(TAG, "onSingleTapUp");
                return true;
            }

            @Override
            public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
//                Log.d(TAG, "onScroll");
                if (mSelectedView != null) {
                    scrollSelectView(distanceX, distanceY);
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onLongPress(@NonNull MotionEvent e) {
                Log.d(TAG, "onLongPress");
                if (mSelectedView != null) {
                    Toast.makeText(getContext(), mSelectedView.getText(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
                Log.d(TAG, "onFling");
                return false;
            }
        });
    }

    private void scrollSelectView(float distanceX, float distanceY) {
        mSelectedView.setTranslationX(mSelectedView.getTranslationX() - distanceX);
        mSelectedView.setTranslationY(mSelectedView.getTranslationY() - distanceY);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_UP) {
            Log.d(TAG, "ACTION_UP");
            if (mSelectedView != null){
                releaseView(event);
            }
        } else if (action == MotionEvent.ACTION_POINTER_DOWN) {
            int pointerIndex = event.getActionIndex();
            float x = event.getX(pointerIndex);
            float y = event.getY(pointerIndex);
            Log.d(TAG, "ACTION_POINTER_DOWN");
        }
        else if (action == MotionEvent.ACTION_DOWN) {
            RectF rectF = new RectF();
            for (int i=0; i<getChildCount(); i++) {
                TextView textView = (TextView)getChildAt(i);
                rectF.set(textView.getLeft(), textView.getTop(), textView.getRight(), textView.getBottom());
                if (rectF.contains(event.getX(), event.getY())) {
                    selectView(textView, i);
                    Log.d(TAG, "click " + textView.getText());
                    break;
                }
            }
        }
        return mDetector.onTouchEvent(event);
    }

    public TagCloud(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.TagCloud);
        mHorizontalMargin = typedArray.getDimension(R.styleable.TagCloud_hMargin, 20);
        mVerticalMargin = typedArray.getDimension(R.styleable.TagCloud_vMargin, 10);
        mTextSize = typedArray.getDimension(R.styleable.TagCloud_textSize, 20);
        mTextPadding = (int) typedArray.getDimension(R.styleable.TagCloud_textPadding, 10);
        typedArray.recycle();
        mDetectorInit();
    }

    public void setTags(List<String> tags) {
        for (String tag: tags) {
            TextView textView = new TextView(getContext());
            textView.setText(tag);
            addView(textView);
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        assert child instanceof TextView;
        TextView textView = (TextView) child;
        textView.setTextSize(mTextSize);
        textView.setMaxLines(1);
        textView.setBackgroundResource(R.drawable.rounded_rectangle_blue_bg);
        textView.setPadding(mTextPadding, mTextPadding,mTextPadding, mTextPadding);
        super.addView(textView, index, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int childWidthSpec;
        int childHeightSpec = 0;
        switch (widthSpecMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                childWidthSpec = MeasureSpec.makeMeasureSpec(
                        widthSpecSize - (int)mHorizontalMargin * 2,
                        MeasureSpec.AT_MOST
                );
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                childWidthSpec = widthMeasureSpec;
                break;
        }


        switch (heightSpecMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                childHeightSpec = MeasureSpec.makeMeasureSpec(
                        heightSpecSize - (int)mVerticalMargin * 2,
                        MeasureSpec.AT_MOST
                );
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                childHeightSpec = heightMeasureSpec;
                break;
        }

        int height = 0;
        int remainWidth = 0;
        int top = 0;

        for (int i=0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(childWidthSpec, childHeightSpec);
            int l, t, r, b;
            if (height == 0 || remainWidth + mHorizontalMargin + child.getMeasuredWidth() > widthSpecSize) {
                t = height + (int)mVerticalMargin;
                top = t;
                height += mVerticalMargin + child.getMeasuredHeight();
                b = height;
                remainWidth = (int)mHorizontalMargin;
                l = remainWidth;
                remainWidth += child.getMeasuredWidth();
                r = remainWidth;
            } else {
                t = top;
                b = top + child.getMeasuredHeight();
                l = remainWidth + (int)mHorizontalMargin;
                remainWidth +=  mHorizontalMargin + child.getMeasuredWidth();
                r = remainWidth;
            }
            @SuppressLint("DrawAllocation") Location location = new Location(l, t, r, b);
            child.setTag(location);
        }
        setMeasuredDimension(widthSpecSize, heightSpecSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        for (int i=0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Location location = (Location) child.getTag();
            child.layout(location.left, location.top, location.right, location.bottom);
        }
    }

    public void changeLayout() {
        TextView textView1 = (TextView)getChildAt(0);
        TextView textView2 = (TextView)getChildAt(getChildCount()-1);
        String temString = (String) textView1.getText();
        textView1.setText(textView2.getText());
        textView2.setText(temString);
    }
}
