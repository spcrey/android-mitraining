package com.example.homework_day_08;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.homework_day_08.view.TagCloud;

import java.util.Arrays;
import java.util.List;

public class HomeworkActivity extends AppCompatActivity {

    private final String TAG = "TestActivity";

    GestureDetector mDetector = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);
        Log.d(TAG, "onCreate");
        test();
        mDetectorInit();

        TagCloud tagCloud = findViewById(R.id.tag_cloud);
        List<String> tags = Arrays.asList(
                "Tag 1", "Tag 2", "Tag 33333333333333", "Tag 4",
                "Tag 1", "Tag 2", "Tag 3", "Tag 4",
                "Tag 5", "Tag 6", "Tag 7", "Tag 8",
                "Tag 9", "Tag 10", "Tag 11", "Tag 12"
        );
        tagCloud.setTags(tags);
        addButton();
    }

    private void addButton() {
        Button button = new Button(this);
        LinearLayout layout = findViewById(R.id.layout_activity_test);
        button.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelSize(R.dimen.button_height))
        );
        button.setText("change tag of head and tail");
        button.setBackgroundResource(R.drawable.rounded_button_bg);
        ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams)
                        button.getLayoutParams();
        int margin = getResources().getDimensionPixelSize(R.dimen.margin);
        params.setMargins(margin, margin, margin, margin);
        button.setLayoutParams(params);
        layout.addView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TagCloud tagCloud = findViewById(R.id.tag_cloud);
                tagCloud.changeLayout();
            }
        });
    }

    private void mDetectorInit() {
        mDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(@NonNull MotionEvent e) {
                Log.d(TAG, "onDown");
                return false;
            }

            @Override
            public void onShowPress(@NonNull MotionEvent e) {
                Log.d(TAG, "onShowPress");
            }

            @Override
            public boolean onSingleTapUp(@NonNull MotionEvent e) {
                Log.d(TAG, "onSingleTapUp");
                return false;
            }

            @Override
            public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
                Log.d(TAG, "onScroll");
                return false;
            }

            @Override
            public void onLongPress(@NonNull MotionEvent e) {
                Log.d(TAG, "onLongPress");
            }

            @Override
            public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
                Log.d(TAG, "onFling");
                return false;
            }
        });

        mDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
                Log.d(TAG, "onSingleTapConfirmed");
                return false;
            }

            @Override
            public boolean onDoubleTap(@NonNull MotionEvent e) {
                Log.d(TAG, "onDoubleTap");
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(@NonNull MotionEvent e) {
                Log.d(TAG, "onDoubleTapEvent");
                return false;
            }
        });
    }

    private void test() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = mDetector.onTouchEvent(event);
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            Log.d(TAG, "ACTION_UP");
        }
        return result;
    }
}