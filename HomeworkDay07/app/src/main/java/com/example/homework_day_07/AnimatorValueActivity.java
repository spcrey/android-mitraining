package com.example.homework_day_07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AnimatorValueActivity extends AppCompatActivity {

    private static final String TAG = "ValueAnimatorActivity";

    private ValueAnimator load_dynamic(TextView textView) {
        ValueAnimator valueAnimator =
                ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                textView.setRotationX(currentValue * 360f);
                textView.setTranslationX(currentValue * 200f);
                Log.d(TAG, "[VALUE]" + currentValue);
            }
        });
        valueAnimator.setStartDelay(500);
        valueAnimator.setDuration(1000);
        return valueAnimator;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator_value);
        TextView textView = findViewById(R.id.value_text);
        ValueAnimator valueAnimator = load_dynamic(textView);
        valueAnimator.start();
    }
}