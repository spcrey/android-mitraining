package com.example.homework_day_07;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AnimatorObjectActivity extends AppCompatActivity {

    private ObjectAnimator load_static(TextView textView) {
        ObjectAnimator objectAnimator =
                (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.animator_rotate);
        objectAnimator.setTarget(textView);
        return objectAnimator;
    }

    private ObjectAnimator load_dynamic(TextView textView) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(
                textView, View.ROTATION_X, 0f, 360f
        );
        objectAnimator.setDuration(1000);
        objectAnimator.setStartDelay(1000);
        return objectAnimator;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator_object);

        TextView textView = findViewById(R.id.object_text);
        ObjectAnimator objectAnimator = load_dynamic(textView);
        objectAnimator.start();
    }
}