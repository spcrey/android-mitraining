package com.example.homework_day_07;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class EvaluatorColorActivity extends AppCompatActivity {

    private ObjectAnimator loadInt(TextView textView) {
        return ObjectAnimator.ofInt(
                textView, "backgroundColor",
                Color.parseColor("#009688"),
                Color.RED
        );
    }

    private ObjectAnimator loadIntWithEvaluator(TextView textView) {
        ObjectAnimator objectAnimator = loadInt(textView);
        objectAnimator.setEvaluator(new ArgbEvaluator());
        return objectAnimator;
    }

    private ObjectAnimator loadArgb(TextView textView) {
        return ObjectAnimator.ofArgb(
                textView, "backgroundColor",
                Color.parseColor("#009688"),
                Color.RED
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluator_color);
        TextView textView =findViewById(R.id.color_text);
        ObjectAnimator objectAnimator = loadArgb(textView);
        objectAnimator.setDuration(5000);
        objectAnimator.setRepeatCount(5);
        objectAnimator.start();
    }
}
